package it.besolution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.besolution.dto.SolutionDto;
import it.besolution.model.Solution;
import it.besolution.repository.SolutionRepository;

@Service
public class SolutionService {
	
	@Autowired
	private SolutionRepository  solutionRepository;

	public Solution createSolution(Solution solution) throws Exception {
		return solutionRepository.save(solution);
	}

	public List<SolutionDto> getSolution() throws Exception{
		return solutionRepository.getAllSolutions();
	}
	
	public Solution update(Solution solution) throws Exception {
		return solutionRepository.update(solution);
	}

	public Solution getSolutionById(Integer solutionId) throws Exception {
		return solutionRepository.getSolutionById(solutionId);
	}
}
