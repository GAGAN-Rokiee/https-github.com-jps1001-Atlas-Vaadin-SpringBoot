package it.besolution.service.workflow;

import it.besolution.model.workflow.WorkFlowAdvanced;
import it.besolution.repository.workflow.WorkFlowAdvancedRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class WorkFlowAdvancedService {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowAdvancedService.class);

    @Autowired
    private WorkFlowAdvancedRepository workFlowAdvancedRepository;

    @Value("${jar.file-path:}")
    private String jarUploadPath;

    public WorkFlowAdvanced saveJarFileAndItsProperties(WorkFlowAdvanced req
            , Integer workFlowId, Boolean isUpdate) throws Exception {

        req.setJarPath(req.getJarPath());
        req.setWorkFlowId(workFlowId);

        return isUpdate
                ? workFlowAdvancedRepository.updateJarRecord(req)
                : workFlowAdvancedRepository.saveJarRecord(workFlowId, req);
    }

    public List<WorkFlowAdvanced> getAllJarPropertiesRelatedToWorkFlow(Integer workFlowId) throws Exception {

        return workFlowAdvancedRepository.getAllJarForWorkFlow(workFlowId, null);
    }

    private String uploadJarToFs(MultipartFile file, Integer workFlowId, Integer propertyId) throws Exception {

        jarUploadPath = StringUtils.isEmpty(jarUploadPath) ? System.getProperty("user.home") : jarUploadPath;

        // Deleting old file upon updating
        if (workFlowId != null && workFlowId > 0 && propertyId != null && propertyId > 0) {
            List<WorkFlowAdvanced> jarDetail = workFlowAdvancedRepository.getAllJarForWorkFlow(workFlowId, propertyId);

            if (CollectionUtils.isEmpty(jarDetail)) {
                throw new IllegalArgumentException(
                        String.format("No property for sol id: %s property id: %s found", workFlowId, propertyId));
            }

            String uploadedFile = jarUploadPath + File.separator
                    + "workflow-jars" + File.separator + jarDetail.get(0).getJarPath();

            LOG.info("Deleting old file: {}", uploadedFile);
            FileUtils.forceDelete(new File(uploadedFile));
            LOG.info("Deleted old file: {}", uploadedFile);
        }

        // Insert new file
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!StringUtils.equalsIgnoreCase(ext, "jar")) {
            throw new IllegalArgumentException("Illegal file format. Only jars are allowed.");
        }

        String fileName = file.getOriginalFilename();
        String uploadPath = jarUploadPath + File.separator + "workflow-jars" + File.separator + fileName;

        file.transferTo(new File(uploadPath));

        return uploadPath;
    }
}
