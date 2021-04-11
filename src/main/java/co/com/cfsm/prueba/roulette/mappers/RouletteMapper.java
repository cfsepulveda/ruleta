package co.com.cfsm.prueba.roulette.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import co.com.cfsm.prueba.roulette.dto.CreateRouletteDto;
import co.com.cfsm.prueba.roulette.dto.RouletteResponseDto;
import co.com.cfsm.prueba.roulette.model.Roulette;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RouletteMapper {

	@Mapping(source = "state", target = "state")
	Roulette createRouletteDtoToRoulette(CreateRouletteDto createRouletteDto);

	@Mapping(source = "state", target = "state")
	@Mapping(source = "id", target = "id")
	List<RouletteResponseDto> rouletteToRouletteResponseDto(Iterable<Roulette> iterable);

}
