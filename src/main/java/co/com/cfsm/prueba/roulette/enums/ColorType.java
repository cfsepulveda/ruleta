package co.com.cfsm.prueba.roulette.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ColorType {
  RED("red"), BLACK("black");

  private String value;

  @JsonValue
  public String getValue() {
    return value;
  }
}

