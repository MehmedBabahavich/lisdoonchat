package net.yarik.todolist.service;

import net.yarik.todolist.controller.ApiController;
import net.yarik.todolist.model.Comment;
import net.yarik.todolist.model.Post;
import net.yarik.todolist.repository.CommentRepository;
import net.yarik.todolist.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostingService {

    private Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    public List<Post> getAllPosts() {

        return postRepository.findAllOrderByLastBumpDesc();
    }

    public Optional<Post> getPostById(Long id) {

        return postRepository.findById(id);
    }

    public List<Comment> getAllPostComments(Long postId) {

        return commentRepository.findByPostId(postId);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Comment createComment(Comment comment) {
        comment = commentRepository.save(comment);

        Optional<Post> originalPost = postRepository.findById(comment.getPostId());
        originalPost.ifPresent(post -> {
            post.setLastBump(LocalDateTime.now());
            post.setCommentCount(post.getCommentCount() + 1L);

            postRepository.save(post);
        });

        return comment;
    }
}
