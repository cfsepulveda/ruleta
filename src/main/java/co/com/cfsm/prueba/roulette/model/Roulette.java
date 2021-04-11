package co.com.cfsm.prueba.roulette.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@RedisHash("roulettes")
public class Roulette {

  @Id
  private String id;

  private String name;

  private String state;

  private List<Bet> bets;

}
