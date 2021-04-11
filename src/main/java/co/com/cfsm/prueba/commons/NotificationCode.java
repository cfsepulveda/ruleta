package co.com.cfsm.prueba.commons;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public enum NotificationCode {

  USR_S_1("User was created successfully",HttpStatus.OK), 
  USR_F_1("User already exists", HttpStatus.FORBIDDEN),
  USR_F_2("User not found", HttpStatus.NOT_FOUND),
  USR_F_3("User not credit", HttpStatus.NOT_FOUND),
  USR_F_4("User error updating ", HttpStatus.INTERNAL_SERVER_ERROR),

  
  RLT_S_1("Roulete was created successfully", HttpStatus.OK),
  RLT_S_2("Roulete query successfully", HttpStatus.OK),
  RLT_U_1("Roulete opening successfully", HttpStatus.OK),
  RLT_U_2("Roulete closed successfully", HttpStatus.OK),
  RLT_F_1("Roulete id not found", HttpStatus.NOT_FOUND),
  RLT_F_2("Roulete is closed", HttpStatus.FORBIDDEN),
  
  BET_S_1("Bet was created successfully", HttpStatus.OK),
  BET_F_1("Bet sent color or number", HttpStatus.BAD_REQUEST),
  


  
  JVE_F_1(StringUtils.EMPTY,HttpStatus.BAD_REQUEST);

  private String description;

  private HttpStatus httpStatus;

  NotificationCode(String description, HttpStatus httpStatus) {
    this.description = description;
    this.httpStatus = httpStatus;
  }


}
