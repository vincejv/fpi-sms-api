package com.abavilla.fpi.repo;

import com.abavilla.fpi.dto.IDto;
import io.smallrye.mutiny.Uni;

//@Path("/extensions")
//@RegisterRestClient(configKey = "m360-api")
public interface IApiRepo<DTO> {
  Uni<IDto> post(DTO dto);
}
