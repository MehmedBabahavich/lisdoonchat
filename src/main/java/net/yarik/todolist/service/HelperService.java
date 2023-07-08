package net.yarik.todolist.service;

import net.yarik.todolist.model.BannedUser;
import net.yarik.todolist.repository.BannedUserRepository;
import net.yarik.todolist.repository.CommentRepository;
import net.yarik.todolist.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class HelperService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BannedUserRepository bannedUserRepository;

    public String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex);
        }
        return "";
    }

    public boolean fileExists(String fileName) {
        if ((postRepository.findByImageName(fileName).isEmpty()) && (commentRepository.findByImageName(fileName).isEmpty())) {
            return false;
        }
        return true;
    }

    public String generateUniqueFileName(String originalFileName) {
        String extension = getFileExtension(originalFileName);

        String uniqueName;
        do {
            uniqueName = UUID.randomUUID().toString() + extension;
        } while (fileExists(uniqueName));

        return uniqueName;
    }

    public String tokenCheck(String token) {
        String adminToken = "13/50";
        if (token.equals(adminToken)) {
            return "admin";
        }

        List<BannedUser> bannedUser = bannedUserRepository.findByToken(token);
        if (!bannedUser.isEmpty()) {
            return "banned";
        }

        return "user";
    }
}
