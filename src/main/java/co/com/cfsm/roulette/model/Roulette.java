package co.com.cfsm.roulette.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.Data;

@Data
@RedisHash("roulettes")
public class Roulette {

  @Id
  private String id;

  private String name;

  private String state;

}
