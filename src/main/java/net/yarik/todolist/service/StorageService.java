package net.yarik.todolist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {

    private Logger log = LoggerFactory.getLogger(StorageService.class);

    private final ResourceLoader resourceLoader;

    @Value("${imagefolder.path}")
    private String imageFolderPath;

    @Autowired
    public StorageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String uploadFileToFileSystem(MultipartFile file) throws IOException {
        Resource resource = new ClassPathResource("uploaded_images/" + file.getOriginalFilename());
        Path filePath = resource.getFile().toPath();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return file.getOriginalFilename();
    }

    public byte[] downloadFileFromFileSystem(String fileName) throws IOException {
        Resource resource = new ClassPathResource("uploaded_images/" + fileName);
        return FileCopyUtils.copyToByteArray(resource.getInputStream());
    }
}
