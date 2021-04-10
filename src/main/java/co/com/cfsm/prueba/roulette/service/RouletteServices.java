package co.com.cfsm.prueba.roulette.service;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.stereotype.Service;
import co.com.cfsm.prueba.commons.NotificationCode;
import co.com.cfsm.prueba.commons.exeptions.RouletteBusinessException;
import co.com.cfsm.prueba.roulette.dto.BetRequestDto;
import co.com.cfsm.prueba.roulette.dto.CreateRouletteDto;
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

	public String opening(String id) throws RouletteBusinessException {
		Roulette roulette = rouletteRepository.findById(id)
				.orElseThrow(() -> new RouletteBusinessException(NotificationCode.RLT_F_1));
		roulette.setState("opening");
		return rouletteRepository.save(roulette).getState();
	}

	public Optional<String> findById(String id) {
		return rouletteRepository.findById(id)
				.filter(roulette -> roulette.getState().equalsIgnoreCase("opening"))
				.map(Roulette::getId);
	}

	public String saveBet(BetRequestDto betRequestDto, String documentNumber) {
		Optional<Roulette> roulette = rouletteRepository.findById(betRequestDto.getIdRoulette());
		Bet bet = Bet.builder().color(betRequestDto.getColor().getValue())
				.amount(betRequestDto.getAmount()).documentNumber(documentNumber)
				.number(betRequestDto.getNumber()).build();
		if (roulette.isPresent() && roulette.get().getBets() == null) {
			roulette.get().setBets(Arrays.asList(bet));
		} else {
			roulette.get().getBets().add(bet);
		}
		return rouletteRepository.save(roulette.get()).getId();
	}
}
