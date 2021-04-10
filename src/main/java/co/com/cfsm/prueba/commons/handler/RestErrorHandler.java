package co.com.cfsm.prueba.commons.handler;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import co.com.cfsm.prueba.commons.NotificationCode;
import co.com.cfsm.prueba.commons.dto.ApiResponse;
import co.com.cfsm.prueba.commons.dto.ErrorDetail;
import co.com.cfsm.prueba.commons.dto.Notification;
import co.com.cfsm.prueba.commons.exeptions.RouletteBusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("MethodArgumentNotValidException exception", ex);

    NotificationCode failureCode = NotificationCode.JVE_F_1;
    StringBuilder errorDescription = new StringBuilder();
    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    errors.stream().forEach(error -> errorDescription.append(error.getField())
        .append(StringUtils.SPACE).append(error.getDefaultMessage()).append(StringUtils.SPACE));

    Notification notification = Notification.builder().code(failureCode.name())
        .description(errorDescription.toString()).build();

    return ResponseEntity.status(failureCode.getHttpStatus())
        .body(new ApiResponse<>(errors, notification));
  }


  @ExceptionHandler(RouletteBusinessException.class)
  public ResponseEntity<ApiResponse<Object>> handleRouletteBusinessException(
      RouletteBusinessException ex) {

    NotificationCode notificationCode = ex.getErrorCode();

    Notification notification = Notification.builder().code(notificationCode.name())
        .description(notificationCode.getDescription()).build();

    return ResponseEntity.status(notificationCode.getHttpStatus())
        .body(new ApiResponse<>(null, notification));
  }



}
