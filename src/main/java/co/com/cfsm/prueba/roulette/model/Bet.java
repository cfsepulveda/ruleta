package co.com.cfsm.prueba.roulette.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bet {

	private String documentNumber;

	private String color;

	private Integer number;

	private BigDecimal amount;


}
