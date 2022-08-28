package com.abavilla.fpi.dto.impl.sms;

import com.abavilla.fpi.dto.AbsDto;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class ErrorLogDto extends AbsDto {
  @BsonProperty("Message")
  private String message;
  @BsonProperty("StackTrace")
  private String stackTrace;
  @BsonProperty("Payload")
  private Object payload;
  @BsonProperty("DateCreated")
  private LocalDateTime dateCreated;
  @BsonProperty("DateUpdated")
  private LocalDateTime dateUpdated;
}
