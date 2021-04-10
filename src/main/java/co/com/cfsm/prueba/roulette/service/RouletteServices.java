package co.com.cfsm.roulette.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import co.com.cfsm.roulette.dto.CreateRouletteDto;
import co.com.cfsm.roulette.exeptions.RouletteBusinessException;
import co.com.cfsm.roulette.mappers.RouletteMapper;
import co.com.cfsm.roulette.model.Roulette;
import co.com.cfsm.roulette.repository.RouletteRepository;
import lombok.AllArgsConstructor;

@Service

@AllArgsConstructor
public class RouletteServices {


  private RouletteRepository rouletteRepository;
  private RouletteMapper mapper;


  public String create(CreateRouletteDto createRouletteDto) throws RouletteBusinessException {

    if (rouletteRepository.existsByName(createRouletteDto.getName())) {
      throw new RouletteBusinessException(
          "roulette already exists - " + createRouletteDto.getName());
    }
    Roulette roulette = mapper.createRouletteDtoToRoulette(createRouletteDto);
    return rouletteRepository.save(roulette).getId();
  }

  public String opening(String id) throws RouletteBusinessException {

    Roulette roulette = rouletteRepository.findById(id)
        .orElseThrow(() -> new RouletteBusinessException("roulette no exists"));

    roulette.setState("opening");

    return rouletteRepository.save(roulette).getState();
  }

}
