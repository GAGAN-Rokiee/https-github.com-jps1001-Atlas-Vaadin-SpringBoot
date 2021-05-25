package it.besolution.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.besolution.dto.SolutionDto;
import it.besolution.model.Solution;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.SolutionService;
import it.besolution.utils.ResponseUtil;

@RestController
@RequestMapping("/api/solution")
public class SolutionController {

    private static final Logger LOG = LoggerFactory.getLogger(SolutionController.class);

    @Autowired
    private SolutionService solutionService;


    @PostMapping("/save")
    public ResponseEntity<ApiRestResponse> createSolution (@RequestBody Solution solution){
        ApiRestResponse response = null;
        try {
            LOG.info("Saving a solution");
            solution = solutionService.createSolution(solution);
            response = new ApiRestResponse();
            response.setData(solution);
            LOG.info("Saving the solution complete");
        } catch (Exception ex) {

            LOG.error("Error while saving the solution. Error: {}", ex.getMessage());
            response = ResponseUtil.returnApiResponse(new ApiRestResponse(), ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<ApiRestResponse> getSolutions(){

        ApiRestResponse response = null;
        try {
            LOG.info("Getting all solution");
            List<SolutionDto>  solutions= solutionService.getSolution();
            response = new ApiRestResponse();
            response.setData(solutions);
            LOG.info("Getting all solution complete");
        } catch (Exception ex) {

            LOG.error("Error while all solution. Error: {}", ex.getMessage());
            response = ResponseUtil.returnApiResponse(new ApiRestResponse(), ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);

    }

    @GetMapping("/{solutionId}")
    public ResponseEntity<ApiRestResponse> getSolutions(@PathVariable Integer solutionId){

        ApiRestResponse response = null;
        try {
            LOG.info("Getting solution for Id: {}", solutionId);
            final Solution solutionById = solutionService.getSolutionById(solutionId);
            response = new ApiRestResponse();
            response.setData(solutionById);
            LOG.info("Solution with Id: {} fetched", solutionId);
        } catch (Exception ex) {

            LOG.error("Error while all solution. Error: {}", ex.getMessage());
            response = ResponseUtil.returnApiResponse(new ApiRestResponse(), ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiRestResponse> update(@RequestBody Solution solution){
        ApiRestResponse response = null;
        try {
            LOG.info("Update a solution");
            solution = solutionService.update(solution);
            response = new ApiRestResponse();
            response.setData(solution);
            LOG.info("Update the solution complete");
        } catch (Exception ex) {

            LOG.error("Error while updating the solution. Error: {}", ex.getMessage());
            response = ResponseUtil.returnApiResponse(new ApiRestResponse(), ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }
}
