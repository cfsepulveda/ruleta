package co.com.cfsm.roulette.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.com.cfsm.roulette.dto.CreateRouletteDto;
import co.com.cfsm.roulette.model.Roulette;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RouletteMapper {

	@Mapping(source = "name", target = "name")
	@Mapping(source = "state", target = "state")
	Roulette createRouletteDtoToRoulette(CreateRouletteDto createRouletteDto);

}
