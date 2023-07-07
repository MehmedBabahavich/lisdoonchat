package net.yarik.todolist.controller;

import net.yarik.todolist.Helper;
import net.yarik.todolist.exceptions.UserIsBannedException;
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

    @RequestMapping(method = RequestMethod.GET, value = "/posts/{id}")
    public ResponseEntity getPostById(@PathVariable("id") Long id) {
        log.info("requested GET on /api/posts/" + id);
        return ResponseEntity.ok()
                .body(postingService.getPostById(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{post_id}")
    public ResponseEntity getAllPostComments(@PathVariable("post_id") Long postId) {
        log.info("requested GET on /api/comments/" + postId);
        return ResponseEntity.ok()
                .body(postingService.getAllPostComments(postId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/posts", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity createPost(@RequestParam(name = "title") String postTitle,
                                     @RequestParam(name = "body") String postBody,
                                     @RequestParam(name = "image", required = false) MultipartFile postImage,
                                     @RequestParam(name = "token", required = false) String token) throws IOException {
        log.info("requested POST on /api/posts");

        if (postImage != null) {
            String fileExtension = Helper.getFileExtension(postImage.getOriginalFilename()).toLowerCase();
            if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg") && !fileExtension.equals(".jpeg")) {
                return ResponseEntity.badRequest().body("unsupported media type");
            }
        }

        try {
            Post createdPost = postingService.createPost(postTitle, postBody, postImage, token);

            log.info("posted to database: \n");
            log.info("title: " + createdPost.getTitle());
            log.info("body: " + createdPost.getBody());
            log.info("imageName: " + createdPost.getImageName());
            log.info("createdAt: " + createdPost.getCreatedAt());
            log.info("bumpTime: " + createdPost.getLastBump());

            return ResponseEntity.ok("Post created successfully");
        } catch (UserIsBannedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user is banned");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comments/{post_id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity createComment(@PathVariable("post_id") Long postId,
                                        @RequestParam(name = "body") String commentBody,
                                        @RequestParam(name = "replyTo", required = false) Long repliedToCommentId,
                                        @RequestParam(name = "image", required = false) MultipartFile commentImage,
                                        @RequestParam(name = "token", required = false) String token) throws IOException {
        log.info("requested POST on /api/comments/" + postId);

        if (commentImage != null) {
            String fileExtension = Helper.getFileExtension(commentImage.getOriginalFilename()).toLowerCase();
            if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg") && !fileExtension.equals(".jpeg")) {
                return ResponseEntity.badRequest().body("unsupported media type");
            }
        }

        try {
            Comment savedComment = postingService.createComment(postId, commentBody, repliedToCommentId, commentImage, token);

            log.info("posted to database: \n");
            log.info("postId: " + savedComment.getPostId());
            log.info("body: " + savedComment.getBody());
            log.info("imageName: " + savedComment.getImageName());
            log.info("createdAt: " + savedComment.getCreatedAt());
            log.info("repliedTo: " + savedComment.getRepliedToCommentId());

            return ResponseEntity.ok().body("Comment created successfully");
        } catch (UserIsBannedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user is banned");
        }
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
