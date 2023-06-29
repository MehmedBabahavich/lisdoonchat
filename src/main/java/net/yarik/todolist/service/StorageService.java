package net.yarik.todolist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class StorageService {

    private Logger log = LoggerFactory.getLogger(StorageService.class);
    @Autowired
    private Environment environment;

    public String uploadFileToFileSystem(MultipartFile file) throws IOException {
        String folderPath = environment.getProperty("imagefgolder.path");
        log.info("environment path is: " + folderPath);
        String filepath = folderPath + file.getOriginalFilename();
        log.info("filepath is: " + filepath);
        file.transferTo(new File(filepath));
        return filepath;
    }

    public byte[] downloadFileFromFileSystem(String filePath) throws IOException {
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        return file;
    }
}
