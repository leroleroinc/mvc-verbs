package com.lerolero.verbs.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;

import com.lerolero.verbs.models.Verb;

public interface MongoVerbRepository extends MongoRepository<Verb,String> {

	@Aggregation("{ $sample: { size: 1 } }")
	public Optional<Verb> pullRandom();

}
