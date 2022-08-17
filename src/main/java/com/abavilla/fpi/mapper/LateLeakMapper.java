package com.abavilla.fpi.mapper;

import com.abavilla.fpi.entity.impl.LateLeakAck;
import com.abavilla.fpi.entity.impl.LeakAck;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, injectionStrategy = InjectionStrategy.FIELD)
public interface LateLeakMapper {
  LateLeakAck toLateLeakAck(LeakAck leakAck);
  LeakAck toLeakAck(LateLeakAck lateLeakAck);
}
