package com.abavilla.fpi.controller.impl.sms;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.MsgTemplateDto;
import com.abavilla.fpi.entity.impl.sms.MsgTemplate;
import com.abavilla.fpi.service.impl.MsgTemplateSvc;

import javax.ws.rs.Path;

@Path("/fpi/msg/template")
public class MsgTemplateResource extends AbsResource<MsgTemplateDto, MsgTemplate, MsgTemplateSvc> {

}