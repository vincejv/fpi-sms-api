package com.abavilla.fpi.service.impl;

import com.abavilla.fpi.dto.impl.NullDto;
import com.abavilla.fpi.entity.enums.ApiStatus;
import com.abavilla.fpi.entity.impl.LateLeakAck;
import com.abavilla.fpi.entity.impl.LeakAck;
import com.abavilla.fpi.entity.impl.MsgReq;
import com.abavilla.fpi.exceptions.ApiSvcEx;
import com.abavilla.fpi.exceptions.LateAckEx;
import com.abavilla.fpi.repo.impl.MsgReqRepo;
import com.abavilla.fpi.service.AbsSvc;
import com.abavilla.fpi.util.MapperUtil;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ApplicationScoped
public class MsgAckSvc extends AbsSvc<NullDto, LeakAck>  {

  public static final int MAX_RETRIES = 5;
  @Inject
  MsgReqRepo msgReqRepo;

  /**
   * Runs background tasks from webhook
   */
  @Inject
  ManagedExecutor executor;

  public Uni<Void> acknowledge(String msgId, String ackStsCde, String ackTimestamp) {
    ApiStatus apiStatus = ApiStatus.fromId(Integer.parseInt(ackStsCde));
    LocalDateTime ackTime = MapperUtil.convertLdtUTC8ToUtc(LocalDateTime.parse(ackTimestamp,
        DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    Uni<Optional<MsgReq>> byMsgId = msgReqRepo.findByMsgId(msgId);

    /* run in background, immediately return response to webhook */
    executor.execute(() -> {
      byMsgId.chain(msgReqOpt -> {
            if (msgReqOpt.isPresent()) {
              return Uni.createFrom().item(msgReqOpt.get());
            } else {
              throw new ApiSvcEx("Message Id for acknowledgement not found: " + msgId);
            }
          })
          .onFailure().retry().withBackOff(Duration.ofSeconds(3)).withJitter(0.2)
          .atMost(5) // Retry for item not found and nothing else
          .chain(msgReq -> {
            if (msgReq.getAckTimestamp() == null) {
              msgReq.setAckTimestamp(ackTime);
            } else {
              if (msgReq.getAckTimestamp().isEqual(ackTime) || msgReq.getAckTimestamp().isEqual(ackTime)) {
                throw new LateAckEx("Acknowledgement already set: " + msgId); // throw late ack
              }
            }
            msgReq.setApiStatus(apiStatus);
            msgReq.setDateUpdated(LocalDateTime.now());
            return msgReqRepo.persistOrUpdate(msgReq);
          })
          .onFailure().call(ex -> {
            LeakAck leak = ex instanceof LateAckEx ? new LateLeakAck() : new LeakAck();
            leak.setDateCreated(LocalDateTime.now());
            leak.setDateUpdated(LocalDateTime.now());
            leak.setMsgId(msgId);
            leak.setApiStatus(apiStatus);
            leak.setTimestamp(ackTime);
            return repo.persist(leak);
          }).onFailure().recoverWithNull() // still has to recover from Exception (LateAck)
          .await().indefinitely();
    });

    return Uni.createFrom().voidItem();
  }

  @Override
  public NullDto mapToDto(LeakAck entity) {
    return null;
  }

  @Override
  public LeakAck mapToEntity(NullDto dto) {
    return null;
  }
}
