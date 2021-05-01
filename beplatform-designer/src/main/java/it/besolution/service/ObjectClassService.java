package it.besolution.service;

import it.besolution.controller.ObjectClassController;
import it.besolution.dto.ObjectClassDto;
import it.besolution.model.ObjectClass;
import it.besolution.repository.ObjectClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ObjectClassService {

    private static final Logger LOG = LoggerFactory.getLogger(ObjectClassService.class);

    @Autowired
    private ObjectClassRepository objectClassRepository;

    public ObjectClass save(ObjectClass objectClass, Integer solutionId) throws Exception {

        LOG.info("Saving the object class");

        String uuidGenerationValue = objectClass.getEntityName() + ":" + new Date().getTime();
        String uniqueId = UUID.nameUUIDFromBytes(uuidGenerationValue.getBytes()).toString();
        LOG.info("Generated UUID is: {}", uniqueId);

        objectClass.setUniqueId(uniqueId);
        objectClass.setSolutionId(solutionId);

        return objectClassRepository.save(objectClass);
    }

    public List<ObjectClassDto> findAll() throws Exception {
        return objectClassRepository.findAll();
    }
}
