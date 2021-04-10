package co.com.cfsm.roulette.repository;

import org.springframework.data.repository.CrudRepository;
import co.com.cfsm.roulette.model.Roulette;

public interface RouletteRepository extends CrudRepository<Roulette, String> {

	public boolean existsByName(String name);

}
