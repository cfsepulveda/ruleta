package co.com.cfsm.prueba.roulette.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.com.cfsm.prueba.commons.NotificationCode;
import co.com.cfsm.prueba.commons.dto.ApiResponse;
import co.com.cfsm.prueba.commons.dto.Notification;
import co.com.cfsm.prueba.commons.exeptions.RouletteBusinessException;
import co.com.cfsm.prueba.roulette.dto.BetRequestDto;
import co.com.cfsm.prueba.roulette.dto.CreateRouletteDto;
import co.com.cfsm.prueba.roulette.dto.RouletteResponseDto;
import co.com.cfsm.prueba.roulette.enums.RouletteStateType;
import co.com.cfsm.prueba.roulette.facade.RouletteFacade;
import co.com.cfsm.prueba.roulette.service.RouletteServices;
import co.com.cfsm.prueba.user.dto.UserDto;
import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("roulette")
@AllArgsConstructor
public class RouletteController {

	private RouletteServices rouletteServices;

	private RouletteFacade rouletteFacade;


	@PostMapping
	public ApiResponse<String> create(@RequestBody CreateRouletteDto createRouletteDto) {

		Notification notification = Notification.builder().code(NotificationCode.RLT_S_1.name())
				.description(NotificationCode.RLT_S_1.getDescription()).build();

		return new ApiResponse<>(rouletteServices.create(createRouletteDto), notification);
	}

	@GetMapping
	public ApiResponse<List<RouletteResponseDto>> getAll() {

		Notification notification = Notification.builder().code(NotificationCode.RLT_S_1.name())
				.description(NotificationCode.RLT_S_2.getDescription()).build();

		return new ApiResponse<>(rouletteServices.findAll(), notification);
	}

	@PutMapping("/{id}")
	public ApiResponse<String> opening(@PathVariable("id") String id)
			throws RouletteBusinessException {

		Notification notification = Notification.builder().code(NotificationCode.RLT_U_1.name())
				.description(NotificationCode.RLT_U_1.getDescription()).build();

		rouletteServices.changeState(id, RouletteStateType.OPENING);

		return new ApiResponse<>(StringUtils.EMPTY, notification);
	}

	/**
	 * the target is that the document number should be sent by claims of a token
	 */
	@PostMapping("/bet")
	public ApiResponse<String> bet(@RequestHeader("Document-Number") String documentNumber,
			@Valid @RequestBody BetRequestDto betRequestDto) throws RouletteBusinessException {

		Notification notification = Notification.builder().code(NotificationCode.RLT_U_1.name())
				.description(NotificationCode.BET_S_1.getDescription()).build();

		return new ApiResponse<>(rouletteFacade.bet(documentNumber, betRequestDto), notification);
	}

	@PutMapping("/{id}/close")
	public ApiResponse<List<UserDto>> close(@PathVariable("id") String id)
			throws RouletteBusinessException, NoSuchAlgorithmException {

		Notification notification = Notification.builder().code(NotificationCode.RLT_U_2.name())
				.description(NotificationCode.RLT_U_2.getDescription()).build();

		return new ApiResponse<>(rouletteFacade.closeBet(id), notification);
	}



}
