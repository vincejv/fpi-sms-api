package com.abavilla.fpi.repo.impl.sms;

import com.abavilla.fpi.entity.impl.sms.MsgReq;
import com.abavilla.fpi.repo.AbsMongoRepo;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class MsgReqRepo extends AbsMongoRepo<MsgReq> {
  public Uni<Optional<MsgReq>> findByMsgId(String msgId){
    return find("messageId", msgId).firstResultOptional();
  }
}
