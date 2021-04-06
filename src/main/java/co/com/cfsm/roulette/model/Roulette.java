package co.com.cfsm.roulette.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "roulettes")
public class Roulette {

	@Id
	private String id;

	private String name;
	
	private String state;

}
