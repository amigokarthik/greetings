package com.example.demo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Greeting;
import com.example.demo.repository.GreetingRepository;

@Service
@Transactional(
		propagation = Propagation.SUPPORTS,
		readOnly = true)
public class GreetingServiceBean implements GreetingService {

	@Autowired
	private GreetingRepository greetingRepository;
		
	@Override
	public Collection<Greeting> findAll() {
		
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	public Greeting findOne(Long id) {
		
		Greeting greeting = greetingRepository.findOne(id);
		return greeting;
	}

	@Override
	@Transactional(
			propagation = Propagation.REQUIRED,
			readOnly = false)
	public Greeting create(Greeting greeting) {
		
		if(greeting.getId() != 0){
			//cannot create greeting with specified value
			return null;
		}
		
		Greeting savedGreeting = greetingRepository.save(greeting);
		return savedGreeting;
		
	}

	@Override
	public Greeting update(Greeting greeting) {
		
		Greeting greetingPersisted = findOne(greeting.getId());
		
		if(greetingPersisted == null){
			//cannot greeting that hasn't been persisted
			return null;
		}
		
		Greeting updatedGreeting = greetingRepository.save(greeting);
		return updatedGreeting;
	}

	@Override
	public void delete(Long id) {
		
		greetingRepository.delete(id);

	}

}
