package it.besolution.service;

import it.besolution.model.Solution;
import it.besolution.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionService {
	
	@Autowired
	private SolutionRepository  solutionRepository;

	public Solution createSolution(Solution solution) throws Exception {
		return solutionRepository.save(solution);
	}

	public List<Solution> getSolution() throws Exception{
		return solutionRepository.getAllSolutions();
	}
	
	public Solution update(Solution solution) throws Exception {
		return solutionRepository.update(solution);
	}

	public Solution getSolutionById(Integer solutionId) throws Exception {
		return solutionRepository.getSolutionById(solutionId);
	}
}
