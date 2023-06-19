package com.lerolero.verbs.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.lerolero.verbs.repositories.MongoVerbRepository;
import com.lerolero.verbs.models.Verb;

@Service
public class VerbService {

	@Autowired
	private MongoVerbRepository repo;

	private String next() {
		return repo.pullRandom()
			.orElseThrow(() -> new RuntimeException("No verb available"))
			.getContinuous();
	}

	public String randomVerb() {
		return next();
	}

	public List<String> randomVerbList(Integer size) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < size; i++) list.add(next());
		return list;
	}

}
