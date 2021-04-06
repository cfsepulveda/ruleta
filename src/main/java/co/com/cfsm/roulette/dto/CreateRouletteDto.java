package co.com.cfsm.roulette.dto;

import lombok.Data;

@Data
public class CreateRouletteDto {

	private static final String STATE_BY_DEFAULT = "new";

	private String name;

	private String state = STATE_BY_DEFAULT;

}
