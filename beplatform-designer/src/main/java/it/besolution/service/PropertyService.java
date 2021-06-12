package it.besolution.service;

import it.besolution.dto.PropertyNameTypeDto;
import it.besolution.model.Property;
import it.besolution.repository.PropertyRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PropertyService {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    private PropertyRepository propertyRepository;
    
  

    public Property save(Property property) throws Exception {
        return propertyRepository.save(property);
    }
    public List<PropertyNameTypeDto> getNameAndTypeBySolutionAndObjectClassId(Integer solutionId,Integer objectClassId) throws Exception 
    { 
    	return propertyRepository.getNameAndTypeBySolutionAndObjectClassId(solutionId,objectClassId); 
    }	 
	public List<Property> findBySolutionIdAndObjectClassId(Integer solutionId, Integer objectClassId) throws Exception 
	{ 
		return  propertyRepository.getAllPropertiesBySolutionAndObjectClassId(solutionId,  objectClassId); 
	}
	public List<Property> getAllPropertiesBySolutionId(Integer solutionId) throws Exception 
	{ 
		return  propertyRepository.getAllPropertiesBySolutionId(solutionId); 
	}
	public Boolean updateProperty(List<Property> propertyList) throws  Exception 
	{ 
	    return propertyRepository.updatePropertyList(propertyList);
	}
	public List<Property> deleteProperty(List<Integer> propertyIds, Integer solutionId, Integer objectClassId) throws  Exception 
	{ 
	    return propertyRepository.deleteProperty(propertyIds,solutionId,objectClassId);
	}
	
}
