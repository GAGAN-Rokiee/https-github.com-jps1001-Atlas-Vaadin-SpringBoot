package it.besolution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.besolution.model.Counter;
import it.besolution.repository.CounterRepository;

@Service
public class CounterService {

	@Autowired
	CounterRepository counterRepository;
	
	public Counter createCounter(Counter counter) throws Exception {
		return counterRepository.save(counter);
	}
	
	public List<Counter> getCountersBySolutionId(Integer solutionId) throws Exception{
		return counterRepository.getCountersBySolutionId(solutionId);
	}
	
	public Counter update(Counter counter) throws Exception {
		return counterRepository.update(counter);
	}
	
	public void  delete(Integer id) throws Exception {
		 counterRepository.delete(id);
	}
	
	public Counter getCounterById(Integer id) throws Exception {
		return counterRepository.getCounterById(id);

	}
	
}
