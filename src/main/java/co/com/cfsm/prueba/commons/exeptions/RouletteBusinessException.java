package co.com.cfsm.prueba.commons.exeptions;

import co.com.cfsm.prueba.commons.NotificationCode;
import lombok.Getter;

@Getter
public class RouletteBusinessException extends Exception {

  private static final long serialVersionUID = 4857617004853307509L;

  private final NotificationCode errorCode;

  public RouletteBusinessException(NotificationCode errorCode) {
    super(errorCode.getDescription());
    this.errorCode = errorCode;
  }


}
