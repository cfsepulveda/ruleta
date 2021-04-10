package co.com.cfsm.prueba.user.controller;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.com.cfsm.prueba.commons.NotificationCode;
import co.com.cfsm.prueba.commons.dto.ApiResponse;
import co.com.cfsm.prueba.commons.dto.Notification;
import co.com.cfsm.prueba.commons.exeptions.RouletteBusinessException;
import co.com.cfsm.prueba.user.dto.UserDto;
import co.com.cfsm.prueba.user.service.UserServices;
import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

  private UserServices userServices;

  @PostMapping
  public ApiResponse<UserDto> create(@RequestBody @Valid UserDto createUserDto)
      throws RouletteBusinessException {

    Notification notification = Notification.builder().code(NotificationCode.USR_S_1.name())
        .description(NotificationCode.USR_S_1.getDescription()).build();

    return new ApiResponse<>(userServices.create(createUserDto), notification);

  }

}
