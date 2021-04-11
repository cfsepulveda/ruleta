package co.com.cfsm.prueba.roulette.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import co.com.cfsm.prueba.roulette.enums.ColorType;
import lombok.Data;

@Data
public class BetRequestDto {

	@NotBlank
	private String idRoulette;

	@Min(value = 0, message = "Value should be greater or equal to 0")
	@Max(value = 36, message = "Value should be less or equal to 36")
	private Integer number;

	private BigDecimal amount;

	private ColorType color;

}
