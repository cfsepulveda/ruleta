package co.com.cfsm.prueba.user.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@RedisHash("users")
public class User {

  @Id
  private String documentNumber;

  private String firstName;

  private String lastName;

  private BigDecimal credit;

}
