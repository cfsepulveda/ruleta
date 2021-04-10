package co.com.cfsm.prueba.commons.dto;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Notification {

  private String description;
  @Builder.Default
  private Timestamp responseTime = new Timestamp(System.currentTimeMillis());
  private String code;

}
