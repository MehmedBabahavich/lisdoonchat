package net.yarik.todolist.controller;

import net.yarik.todolist.model.Post;
import net.yarik.todolist.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private PostService postService;

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    public ResponseEntity getAllPosts() {
        log.info("requested GET on /api/posts");
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{post_id}")
    public ResponseEntity getAllPostComments(@PathVariable("post_id") Long postId) {
        log.info("requested GET on /api/comments/" + postId);
        return ResponseEntity.ok(postService.getAllPostComments(postId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/posts")
    public ResponseEntity createPost(@RequestBody Post postBody) {
        log.info("requested POST on /api/posts");
        Post post = new Post();

        post.setTitle(postBody.getTitle());
        post.setBody(postBody.getBody());
        post.setImagePath(postBody.getImagePath());
        post.setCreatedAt(postBody.getCreatedAt());

        log.info("posted to database: \n");
        log.info("title:" + postBody.getTitle());
        log.info("body:" + postBody.getBody());
        log.info("imagePath:" + postBody.getImagePath());
        log.info("createdAt:" + postBody.getCreatedAt());

        postService.createPost(post);

        return ResponseEntity.ok().body("Post created successfully");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comments/{post_id}/{comment_id}")
    public ResponseEntity createComment() {
        return null;
    }

}
