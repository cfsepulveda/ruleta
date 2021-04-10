package co.com.cfsm.prueba.roulette.facade;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import co.com.cfsm.prueba.commons.NotificationCode;
import co.com.cfsm.prueba.commons.exeptions.RouletteBusinessException;
import co.com.cfsm.prueba.roulette.dto.BetRequestDto;
import co.com.cfsm.prueba.roulette.service.RouletteServices;
import co.com.cfsm.prueba.user.dto.UserDto;
import co.com.cfsm.prueba.user.service.UserServices;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RouletteFacade {

	private RouletteServices rouletteServices;

	private UserServices userServices;


	public String bet(String documentNumber, BetRequestDto betRequestDto)
			throws RouletteBusinessException {
		isOpenRoulette(betRequestDto.getIdRoulette());
		hasCredit(documentNumber, betRequestDto.getAmount());
		spendCustomerCredit(documentNumber, betRequestDto.getAmount());
		return saveBet(betRequestDto, documentNumber);
	}


	private void hasCredit(String documentNumber, BigDecimal credit)
			throws RouletteBusinessException {
		UserDto userDto = userServices.findByDocumentNumber(documentNumber);
		if (userDto.getCredit().compareTo(credit) < 0) {
			throw new RouletteBusinessException(NotificationCode.USR_F_3);
		}
	}

	private void isOpenRoulette(String idRoulette) throws RouletteBusinessException {
		if (!rouletteServices.findById(idRoulette).isPresent()) {
			throw new RouletteBusinessException(NotificationCode.RLT_F_2);
		}
	}

	private void spendCustomerCredit(String documentNumber, BigDecimal credit)
			throws RouletteBusinessException {
		UserDto userDto = userServices.findByDocumentNumber(documentNumber);
		userDto.setCredit(userDto.getCredit().subtract(credit));
		userServices.update(userDto);
	}

	private String saveBet(BetRequestDto betRequestDto, String documentNumber) {
		return rouletteServices.saveBet(betRequestDto, documentNumber);
	}


}
