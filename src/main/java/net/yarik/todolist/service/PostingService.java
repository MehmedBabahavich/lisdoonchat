package net.yarik.todolist.service;

import net.yarik.todolist.controller.ApiController;
import net.yarik.todolist.exceptions.UserIsBannedException;
import net.yarik.todolist.model.BannedUser;
import net.yarik.todolist.model.Comment;
import net.yarik.todolist.model.Post;
import net.yarik.todolist.repository.BannedUserRepository;
import net.yarik.todolist.repository.CommentRepository;
import net.yarik.todolist.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PostingService {

    private Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private HelperService helperService;

    @Autowired
    private StorageService storageService;


    public List<Post> getAllPosts() {

        return postRepository.findAllOrderByLastBumpDesc();
    }

    public Optional<Post> getPostById(Long id) {

        return postRepository.findById(id);
    }

    public List<Comment> getAllPostComments(Long postId) {

        return commentRepository.findByPostId(postId);
    }

    public Post createPost(String postTitle, String postBody, MultipartFile postImage, String token) throws IOException, UserIsBannedException {

        if (helperService.tokenCheck(token).equals("banned")) {
            throw new UserIsBannedException("user is banned");
        }

        Post post = new Post();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        String formattedNow = now.format(formatter);

        String fileName = "";
        if (postImage != null) {
            fileName = storageService.uploadFileToFileSystem(postImage);
        }

        post.setImageName(fileName);
        post.setTitle(postTitle);
        post.setBody(postBody);
        post.setCreatedAt(formattedNow);
        post.setLastBump(LocalDateTime.now());
        post.setCommentCount(0L);

        return postRepository.save(post);
    }

    @Transactional
    public Comment createComment(Long postId, String commentBody, Long repliedToCommentId, MultipartFile commentImage, String token) throws IOException, UserIsBannedException {

        if (helperService.tokenCheck(token).equals("banned")) {
            throw new UserIsBannedException("user is banned");
        }

        Comment comment = new Comment();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        String formattedNow = now.format(formatter);

        String filename = "";
        if (commentImage != null) {
            filename = storageService.uploadFileToFileSystem(commentImage);
        }

        comment.setImageName(filename);
        comment.setPostId(postId);
        comment.setBody(commentBody);
        comment.setCreatedAt(formattedNow);
        comment.setRepliedToCommentId(repliedToCommentId);

        Comment savedComment = commentRepository.save(comment);


        Optional<Post> originalPost = postRepository.findById(comment.getPostId());
        originalPost.ifPresent(post -> {
            post.setLastBump(LocalDateTime.now());
            post.setCommentCount(post.getCommentCount() + 1L);

            postRepository.save(post);
        });

        return savedComment;
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
