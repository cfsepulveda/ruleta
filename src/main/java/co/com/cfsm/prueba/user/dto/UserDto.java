package co.com.cfsm.prueba.user.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class UserDto {

  @Id
  private String documentNumber;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotNull
  @Min(0)
  @Max(10000)
  private BigDecimal credit;

}
