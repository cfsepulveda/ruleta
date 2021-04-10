package co.com.cfsm.roulette.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.cfsm.roulette.dto.CreateRouletteDto;
import co.com.cfsm.roulette.exeptions.RouletteBusinessException;
import co.com.cfsm.roulette.service.RouletteServices;
import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("roulette")
@AllArgsConstructor
public class RouletteController {

  private RouletteServices rouletteServices;

  @PostMapping
  public String create(@RequestBody CreateRouletteDto createRouletteDto)
      throws RouletteBusinessException {
    return rouletteServices.create(createRouletteDto);
  }

  @PutMapping("/{id}")
  public String opening(@PathVariable("id") String id) throws RouletteBusinessException {
    return rouletteServices.opening(id);
  }

  @PostMapping("/bet")
  public String bet(@PathVariable("id") String id) throws RouletteBusinessException {
    return rouletteServices.opening(id);
  }

}
