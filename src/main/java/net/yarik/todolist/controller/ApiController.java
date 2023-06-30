package net.yarik.todolist.controller;

import net.yarik.todolist.model.Comment;
import net.yarik.todolist.model.Post;
import net.yarik.todolist.service.PostingService;
import net.yarik.todolist.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class ApiController {

    private Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private PostingService postingService;

    @Autowired
    private StorageService storageService;

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    public ResponseEntity getAllPosts() {
        log.info("requested GET on /api/posts");
        return ResponseEntity.ok()
                .body(postingService.getAllPosts());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{post_id}")
    public ResponseEntity getAllPostComments(@PathVariable("post_id") Long postId) {
        log.info("requested GET on /api/comments/" + postId);
        return ResponseEntity.ok(postingService.getAllPostComments(postId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/posts", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity createPost(@RequestParam(name = "title") String postTitle,
                                     @RequestParam(name = "body") String postBody,
                                     @RequestParam(name = "image") MultipartFile file) throws IOException {
        log.info("requested POST on /api/posts");
        Post post = new Post();

        log.info("lenght of image: " + file.getBytes().length);

        String fileName = storageService.uploadFileToFileSystem(file);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        String formattedNow = now.format(formatter);

        log.info("file is saved at this time: " + formattedNow);
        log.info("file is saved at: " + fileName);

        post.setTitle(postTitle.toString());
        post.setBody(postBody.toString());
        post.setImageName(fileName);
        post.setCreatedAt(formattedNow);

        postingService.createPost(post);

        log.info("posted to database: \n");
        log.info("title: " + post.getTitle());
        log.info("body: " + post.getBody());
        log.info("imageName: " + post.getImageName());
        log.info("createdAt: " + post.getCreatedAt());

        return ResponseEntity.ok("Post created successfully");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comments/{post_id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity createComment(@PathVariable("post_id") Long postId,
                                        @RequestParam(name = "body") Comment commentBody,
                                        @RequestParam(name = "image") MultipartFile file) throws IOException {
        log.info("requested POST on /api/comments/" + postId);
        Comment comment = new Comment();

        String fileName = storageService.uploadFileToFileSystem(file);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        String formattedNow = now.format(formatter);

        comment.setPostId(postId);
        comment.setBody(commentBody.getBody());
        comment.setImageName(fileName);
        comment.setCreatedAt(formattedNow);

        postingService.createComment(comment);

        log.info("posted to database: \n");
        log.info("postId: " + postId);
        log.info("body: " + comment.getBody());
        log.info("imageName: " + comment.getImageName());
        log.info("createdAt: " + comment.getCreatedAt());

        return ResponseEntity.ok().body("Comment created successfully");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pictures/{picture_name}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable("picture_name") String pictureName) throws IOException{
        log.info("requested GET on /api/pictures/" + pictureName);
        byte[] imageBytes = storageService.downloadFileFromFileSystem(pictureName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
