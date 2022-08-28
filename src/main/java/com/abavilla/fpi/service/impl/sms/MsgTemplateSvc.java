package com.abavilla.fpi.service.impl.sms;

import com.abavilla.fpi.dto.impl.sms.MsgTemplateDto;
import com.abavilla.fpi.entity.impl.sms.MsgTemplate;
import com.abavilla.fpi.service.AbsSvc;
import com.abavilla.fpi.util.MapperUtil;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MsgTemplateSvc extends AbsSvc<MsgTemplateDto, MsgTemplate> {

  @Override
  public MsgTemplateDto mapToDto(MsgTemplate entity) {
    return MapperUtil.mapper().convertValue(entity, MsgTemplateDto.class);
  }

  @Override
  public MsgTemplate mapToEntity(MsgTemplateDto dto) {
    return MapperUtil.mapper().convertValue(dto, MsgTemplate.class);
  }
}