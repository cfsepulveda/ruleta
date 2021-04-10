package co.com.cfsm.prueba.roulette.dto;

import lombok.Data;

@Data
public class CreateRouletteDto {

  private static final String STATE_BY_DEFAULT = "closed";

  private String state = STATE_BY_DEFAULT;

}
