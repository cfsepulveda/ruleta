package co.com.cfsm.prueba.roulette.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import co.com.cfsm.prueba.commons.NotificationCode;
import co.com.cfsm.prueba.commons.exeptions.RouletteBusinessException;
import co.com.cfsm.prueba.roulette.dto.BetRequestDto;
import co.com.cfsm.prueba.roulette.dto.CreateRouletteDto;
import co.com.cfsm.prueba.roulette.enums.RouletteStateType;
import co.com.cfsm.prueba.roulette.mappers.RouletteMapper;
import co.com.cfsm.prueba.roulette.model.Bet;
import co.com.cfsm.prueba.roulette.model.Roulette;
import co.com.cfsm.prueba.roulette.repository.RouletteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RouletteServices {

	private RouletteRepository rouletteRepository;
	private RouletteMapper mapper;


	public String create(CreateRouletteDto createRouletteDto) {
		Roulette roulette = mapper.createRouletteDtoToRoulette(createRouletteDto);
		return rouletteRepository.save(roulette).getId();
	}

	public String changeState(String id, RouletteStateType rouletteStateType)
			throws RouletteBusinessException {
		Roulette roulette = rouletteRepository.findById(id)
				.orElseThrow(() -> new RouletteBusinessException(NotificationCode.RLT_F_1));
		roulette.setState(rouletteStateType.getValue());
		return rouletteRepository.save(roulette).getState();
	}

	public Optional<String> findById(String id) {
		return rouletteRepository.findById(id)
				.filter(roulette -> roulette.getState()
						.equalsIgnoreCase(RouletteStateType.OPENING.getValue()))
				.map(Roulette::getId);
	}

	public String saveBet(BetRequestDto betRequestDto, String documentNumber) {
		Optional<Roulette> roulette = rouletteRepository.findById(betRequestDto.getIdRoulette());

		String color =
				Objects.nonNull(betRequestDto.getColor()) ? betRequestDto.getColor().getValue()
						: null;

		Bet bet = Bet.builder().color(color).amount(betRequestDto.getAmount())
				.documentNumber(documentNumber).number(betRequestDto.getNumber()).build();
		if (roulette.isPresent() && roulette.get().getBets() == null) {
			roulette.get().setBets(Arrays.asList(bet));
		} else {
			roulette.get().getBets().add(bet);
		}
		return rouletteRepository.save(roulette.get()).getId();
	}

	public List<Bet> getBetWinners(String id)
			throws RouletteBusinessException, NoSuchAlgorithmException {

		Roulette roulette = rouletteRepository.findById(id)
				.orElseThrow(() -> new RouletteBusinessException(NotificationCode.RLT_F_1));

		List<Bet> bets = new ArrayList<>();

		Random rand = SecureRandom.getInstanceStrong();

		if (!roulette.getBets().stream().filter(bet -> StringUtils.isEmpty(bet.getColor()))
				.collect(Collectors.toList()).isEmpty()) {
			int aleatorio = rand.nextInt((int) roulette.getBets().stream()
					.filter(bet -> StringUtils.isEmpty(bet.getColor())).count());
			bets.add(roulette.getBets().stream().filter(bet -> StringUtils.isEmpty(bet.getColor()))
					.collect(Collectors.toList()).get(aleatorio));
		}

		if (!roulette.getBets().stream().filter(bet -> !StringUtils.isEmpty(bet.getColor()))
				.collect(Collectors.toList()).isEmpty()) {
			int aleatorio = rand.nextInt((int) roulette.getBets().stream()
					.filter(bet -> !StringUtils.isEmpty(bet.getColor())).count());
			bets.add(roulette.getBets().stream().filter(bet -> !StringUtils.isEmpty(bet.getColor()))
					.collect(Collectors.toList()).get(aleatorio));
		}

		return bets;

	}
}
