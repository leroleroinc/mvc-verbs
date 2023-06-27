package com.lerolero.verbs.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.redisson.api.RedissonClient;
import org.redisson.api.RBucket;

import com.lerolero.verbs.repositories.MongoVerbRepository;
import com.lerolero.verbs.models.Verb;

@Repository
public class VerbCache {

	@Autowired
	private RedissonClient redis;

	@Autowired
	private MongoVerbRepository repo;

	private List<String> ids;

	public Verb next() throws CacheMissException {
		if (ids == null) {
			ids = repo.findAll().stream().map(Verb::getId).collect(Collectors.toList());
		}
		String id = ids.get((int)(Math.random() * ids.size()));
		RBucket<Verb> bucket = redis.getBucket("/verb/" + id);
		if (bucket.get() == null) throw new CacheMissException(id);
		return bucket.get();
	}

	public void add(Verb verb) {
		RBucket<Verb> bucket = redis.getBucket("/verb/" + verb.getId());
		bucket.set(verb);
		ids.add(verb.getId());
	}

	public static class CacheMissException extends Exception {
		private String key;
		public CacheMissException(String key) {
			this.key = key;
		}
		public String getKey() {
			return key;
		}
	}

}
