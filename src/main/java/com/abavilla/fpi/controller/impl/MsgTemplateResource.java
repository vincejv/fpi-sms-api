package com.abavilla.fpi.controller.impl;

import com.abavilla.fpi.controller.AbsResource;
import com.abavilla.fpi.dto.impl.MsgTemplateDto;
import com.abavilla.fpi.entity.impl.MsgTemplate;
import com.abavilla.fpi.service.impl.MsgTemplateSvc;

import javax.ws.rs.Path;

@Path("/msg/template")
public class MsgTemplateResource extends AbsResource<MsgTemplateDto, MsgTemplate, MsgTemplateSvc> {

}