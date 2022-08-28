package com.abavilla.fpi.service.impl.load;

import com.abavilla.fpi.dto.impl.api.load.RewardsCallbackDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.mapper.load.RewardsTransMapper;
import com.abavilla.fpi.service.AbsSvc;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RewardsSvc extends AbsSvc<RewardsCallbackDto, RewardsTransStatus> {
  @Inject
  RewardsTransMapper rewardsMapper;

  public Uni<Void> storeCallback(RewardsCallbackDto dto) {
    var item = mapToEntity(dto);
    return repo.persist(item).replaceWithVoid();
  }

  @Override
  public RewardsCallbackDto mapToDto(RewardsTransStatus entity) {
    RewardsCallbackDto dto = new RewardsCallbackDto();
    RewardsCallbackDto.Body dtoBody = rewardsMapper.mapToDto(entity);
    dto.setOutboundRewardRequest(dtoBody);
    return dto;
  }

  @Override
  public RewardsTransStatus mapToEntity(RewardsCallbackDto dto) {
    return rewardsMapper.mapToEntity(dto.getOutboundRewardRequest());
  }
}
