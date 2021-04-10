package co.com.cfsm.prueba.roulette.controller;

import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
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
import co.com.cfsm.prueba.roulette.enums.RouletteStateType;
import co.com.cfsm.prueba.roulette.facade.RouletteFacade;
import co.com.cfsm.prueba.roulette.service.RouletteServices;
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

	@PutMapping("/{id}")
	public ApiResponse<String> opening(@PathVariable("id") String id)
			throws RouletteBusinessException {

		Notification notification = Notification.builder().code(NotificationCode.RLT_U_1.name())
				.description(NotificationCode.RLT_U_1.getDescription()).build();

		rouletteServices.changeState(id, RouletteStateType.OPENING);

		return new ApiResponse<>(StringUtils.EMPTY, notification);
	}

	@PostMapping("/bet")
	public ApiResponse<String> bet(@RequestHeader("Document-Number") String documentNumber,
			@Valid @RequestBody BetRequestDto betRequestDto) throws RouletteBusinessException {

		Notification notification = Notification.builder().code(NotificationCode.RLT_U_1.name())
				.description(NotificationCode.BET_S_1.getDescription()).build();

		return new ApiResponse<>(rouletteFacade.bet(documentNumber, betRequestDto), notification);
	}



}
