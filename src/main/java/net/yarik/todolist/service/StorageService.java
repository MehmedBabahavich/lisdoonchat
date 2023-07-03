package net.yarik.todolist.service;

import net.yarik.todolist.repository.CommentRepository;
import net.yarik.todolist.repository.PostRepository;
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
import java.util.UUID;

@Service
public class StorageService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Logger log = LoggerFactory.getLogger(StorageService.class);

    @Value("classpath:uploaded_images/")
    private Resource imageUploadDirResource;

    public String uploadFileToFileSystem(MultipartFile file) throws IOException {

        String uploadDirPath = imageUploadDirResource.getFile().getAbsolutePath();
        String generatedName = generateUniqueFileName(file.getOriginalFilename());

        Path filepath = Path.of(uploadDirPath, generatedName);

        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        return generatedName;
    }

    public byte[] downloadFileFromFileSystem(String fileName) throws IOException {
        Resource resource = new ClassPathResource("uploaded_images/" + fileName);
        return FileCopyUtils.copyToByteArray(resource.getInputStream());
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = getFileExtension(originalFileName);

        String uniqueName;
        do {
            uniqueName = UUID.randomUUID().toString() + extension;
        } while (fileExists(uniqueName));

        return uniqueName;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }

    private boolean fileExists(String fileName) {

        log.info("searching for fileNames...");
        if ((postRepository.findByImageName(fileName).isEmpty()) && (commentRepository.findByImageName(fileName).isEmpty())) {
            return false;
        }

        return true;
    }
}
