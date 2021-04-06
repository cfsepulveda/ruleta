package co.com.cfsm.roulette.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.cfsm.roulette.model.Roulette;

public interface RouletteRepository extends MongoRepository<Roulette, String> {

	public boolean existsByName(String name);

}
