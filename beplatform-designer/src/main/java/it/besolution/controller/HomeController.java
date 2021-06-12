package it.besolution.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping
    public String getHome() {
        return "Working";
    }

    @Value("${jar.file-path:}")
    private String jarUploadPath;

    @GetMapping("/get-upload-jars-path")
    public String getUploadJarPath() {

        this.jarUploadPath = StringUtils.isEmpty(this.jarUploadPath)
                ? System.getProperty("user.home")
                : this.jarUploadPath;

        String uploadedFilePath = this.jarUploadPath + File.separator + "workflow-jars" + File.separator;
        if (new File(uploadedFilePath).exists()) {
            new File(uploadedFilePath).mkdir();
        }

        return uploadedFilePath;
    }
}
