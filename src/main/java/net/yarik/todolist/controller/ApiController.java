package net.yarik.todolist.controller;

import net.yarik.todolist.model.Comment;
import net.yarik.todolist.model.Post;
import net.yarik.todolist.service.PostingService;
import net.yarik.todolist.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
                .body(postingService.getAllPosts());  //
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{post_id}")
    public ResponseEntity getAllPostComments(@PathVariable("post_id") Long postId) {
        log.info("requested GET on /api/comments/" + postId);
        return ResponseEntity.ok(postingService.getAllPostComments(postId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/posts", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity createPost(@RequestParam(name = "title") String postTitle,
                                     @RequestParam(name = "postBody") String postBody,
                                     @RequestParam(name = "createdAt") String postCreatedAt,
                                     @RequestParam(name = "imagePath") MultipartFile file) throws IOException {
        log.info("requested POST on /api/posts");
        Post post = new Post();

        String filePath = storageService.uploadFileToFileSystem(file);

        log.info("file is saved at: " + filePath);

        post.setTitle(postTitle.toString());
        post.setBody(postBody.toString());
        post.setImagePath(filePath);
        post.setCreatedAt(postCreatedAt.toString());

        postingService.createPost(post);

        log.info("posted to database: \n");
        log.info("title: " + post.getTitle());
        log.info("body: " + post.getBody());
        log.info("imagePath: " + post.getImagePath());
        log.info("createdAt: " + post.getCreatedAt());

        return ResponseEntity.ok().body("Post created successfully");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comments/{post_id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity createComment(@PathVariable("post_id") Long postId, @RequestParam Comment commentBody, @RequestParam MultipartFile file) throws IOException {
        log.info("requested POST on /api/comments/" + postId);
        Comment comment = new Comment();

        String filepath = storageService.uploadFileToFileSystem(file);

        comment.setPostId(postId);
        comment.setBody(commentBody.getBody());
        comment.setImagePath(filepath);
        comment.setCreatedAt(commentBody.getCreatedAt());

        postingService.createComment(comment);

        log.info("posted to database: \n");
        log.info("postId: " + postId);
        log.info("body: " + comment.getBody());
        log.info("imagePath: " + comment.getImagePath());
        log.info("createdAt: " + comment.getCreatedAt());

        return ResponseEntity.ok().body("Comment created successfully");
    }

}
