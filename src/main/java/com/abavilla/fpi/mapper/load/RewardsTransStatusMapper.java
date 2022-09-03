package com.abavilla.fpi.mapper.load;

import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsReqDto;
import com.abavilla.fpi.dto.impl.api.load.gl.GLRewardsRespDto;
import com.abavilla.fpi.dto.impl.load.LoadRespDto;
import com.abavilla.fpi.entity.impl.load.RewardsTransStatus;
import com.abavilla.fpi.entity.mongo.AbsMongoField;
import com.abavilla.fpi.mapper.load.dtone.DTOneMapper;
import com.abavilla.fpi.mapper.load.gl.GLMapper;
import com.dtone.dvs.dto.TransactionRequest;
import com.dtone.dvs.dto.TransactionResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import javax.inject.Inject;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI,
    injectionStrategy = InjectionStrategy.FIELD)
public abstract class RewardsTransStatusMapper {

  @Inject
  DTOneMapper dtOneMapper;
  @Inject
  GLMapper glMapper;

  @Mappings(value = {
      @Mapping(target = "dateCreated", ignore = true),
      @Mapping(target = "dateUpdated", ignore = true)
  })
  public abstract void mapLoadRespDtoToEntity(LoadRespDto loadRespDto,
                              @MappingTarget RewardsTransStatus dest);

  AbsMongoField anyObjectToAbsField(Object dto) {
    AbsMongoField field = null;
    if (dto instanceof GLRewardsReqDto) {
      field = glMapper.mapGLRewardsReqToEntity((GLRewardsReqDto)dto);
    } else if (dto instanceof GLRewardsRespDto) {
      field = glMapper.mapGLRewardsRespToEntity((GLRewardsRespDto) dto);
    } else if (dto instanceof TransactionRequest) {
      field = dtOneMapper.mapDTOneReqToEntity((TransactionRequest) dto);
    } else if (dto instanceof TransactionResponse) {
      field = dtOneMapper.mapDTOneRespToEntity((TransactionResponse) dto);
    }
    return field;
  }


//  @AfterMapping
//  default void excludeInEntity(AnyDto anyDto,
//                       @MappingTarget AnyItem anyItem) {
//    List<String> excludedFieldsInEntity =
//        List.of("");
//    anyItem.keySet().removeAll(excludedFieldsInEntity);
//  }

//  @Mapping(source = "body.", target = "request.")
//  @Mapping(target = "dateCreated", ignore = true)
//  @Mapping(target = "dateUpdated", ignore = true)
//  void mapGLRequestDtoToEntity(GLRewardsReqDto dto,
//                               @MappingTarget RewardsTransStatus rewardsTransStatus);
//
//  @Mapping(source = "body.", target = "response.")
//  @Mapping(source = "error", target = "response.error")
//  @Mapping(target = "dateCreated", ignore = true)
//  @Mapping(target = "dateUpdated", ignore = true)
//  void mapGLRespDtoToEntity(GLRewardsRespDto dto,
//                            @MappingTarget RewardsTransStatus rewardsTransStatus);
//
//  @Mapping(source = "body.", target = "callback.")
//  @Mapping(target = "dateCreated", ignore = true)
//  @Mapping(target = "dateUpdated", ignore = true)
//  void mapGLCallbackDtoToEntity(GLRewardsCallbackDto dto,
//                                @MappingTarget RewardsTransStatus rewardsTransStatus);
//
//  @Mapping(source = "body.", target = ".")
//  @Mapping(target = "dateCreated", ignore = true)
//  @Mapping(target = "dateUpdated", ignore = true)
//  GLRewardsCallback mapCallbackDtoToCallbackEntity(GLRewardsCallbackDto dto);
}
