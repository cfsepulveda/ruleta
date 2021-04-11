package co.com.cfsm.prueba.roulette.facade;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import co.com.cfsm.prueba.commons.NotificationCode;
import co.com.cfsm.prueba.commons.exeptions.RouletteBusinessException;
import co.com.cfsm.prueba.roulette.dto.BetRequestDto;
import co.com.cfsm.prueba.roulette.enums.RouletteStateType;
import co.com.cfsm.prueba.roulette.model.Bet;
import co.com.cfsm.prueba.roulette.service.RouletteServices;
import co.com.cfsm.prueba.user.dto.UserDto;
import co.com.cfsm.prueba.user.service.UserServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class RouletteFacade {

	private RouletteServices rouletteServices;

	private UserServices userServices;


	public String bet(String documentNumber, BetRequestDto betRequestDto)
			throws RouletteBusinessException {
		validateRequestBet(betRequestDto);
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



	public List<UserDto> closeBet(String idRoulette)
			throws NoSuchAlgorithmException, RouletteBusinessException {

		isOpenRoulette(idRoulette);

		List<Bet> betWinners = rouletteServices.getBetWinners(idRoulette);

		List<UserDto> winnersCustomers = getWinnersCustomer(betWinners);

		winnersCustomers = giveReward(winnersCustomers, betWinners);

		updateWinners(winnersCustomers);

		rouletteServices.changeState(idRoulette, RouletteStateType.CLOSE);

		return winnersCustomers;

	}

	private List<UserDto> getWinnersCustomer(List<Bet> betWinners) {

		List<UserDto> winnersCustomers = new ArrayList<>();

		betWinners.forEach(bet -> {
			try {
				winnersCustomers.add(userServices.findByDocumentNumber(bet.getDocumentNumber()));
			} catch (RouletteBusinessException e) {
				log.error(e.getMessage());
			}
		});

		return winnersCustomers;
	}

	private List<UserDto> giveReward(List<UserDto> winnersCustomers, List<Bet> betWinners) {

		winnersCustomers.forEach(winner -> {
			Bet bet = betWinners.stream()
					.filter(item -> item.getDocumentNumber().equals(winner.getDocumentNumber()))
					.findFirst().get();

			if (Objects.nonNull(bet.getColor())) {
				winner.setCredit(
						winner.getCredit().add((bet.getAmount().multiply(BigDecimal.valueOf(5)))));
			} else {
				winner.setCredit(winner.getCredit()
						.add((bet.getAmount().multiply(BigDecimal.valueOf(1.8)))));
			}
		});

		return winnersCustomers;

	}

	private List<UserDto> updateWinners(List<UserDto> winnersCustomers) {

		winnersCustomers.forEach(winner -> {
			try {
				userServices.update(winner);
			} catch (RouletteBusinessException e) {
				log.error(e.getMessage());
			}
		});
		return winnersCustomers;

	}

	private void validateRequestBet(BetRequestDto betRequestDto) throws RouletteBusinessException {
		if (Objects.nonNull(betRequestDto.getNumber())
				|| Objects.nonNull(betRequestDto.getColor())) {
			if (Objects.nonNull(betRequestDto.getNumber())
					&& Objects.nonNull(betRequestDto.getColor())) {
				throw new RouletteBusinessException(NotificationCode.BET_F_1);
			}
		} else {
			throw new RouletteBusinessException(NotificationCode.BET_F_1);
		}
	}
}
