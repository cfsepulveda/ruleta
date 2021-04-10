package co.com.cfsm.prueba.commons.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetail {

  private String status;
  private int code;
  private String description;
}
