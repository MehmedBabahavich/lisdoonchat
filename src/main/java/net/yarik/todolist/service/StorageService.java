package net.yarik.todolist.service;

import net.yarik.todolist.repository.CommentRepository;
import net.yarik.todolist.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private HelperService helperService;

    private Logger log = LoggerFactory.getLogger(StorageService.class);


    private String currentDirectory;

    public StorageService() {
        this.currentDirectory = System.getProperty("user.dir");
        log.info("current dir is : " + currentDirectory);
    }

    public String uploadFileToFileSystem(MultipartFile file) throws IOException {
        String uploadDirPath = currentDirectory + "/uploaded_images/";
        String generatedName = helperService.generateUniqueFileName(file.getOriginalFilename());

        Path filepath = Path.of(uploadDirPath, generatedName);

        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        return generatedName;
    }

    public byte[] downloadFileFromFileSystem(String fileName) throws IOException {
        Resource resource = new FileSystemResource(currentDirectory + "/uploaded_images/" + fileName);
        return FileCopyUtils.copyToByteArray(resource.getInputStream());
    }
}
