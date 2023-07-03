package net.yarik.todolist.service;

import net.yarik.todolist.model.Comment;
import net.yarik.todolist.model.Post;
import net.yarik.todolist.repository.CommentRepository;
import net.yarik.todolist.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class PostingService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    public List<Post> getAllPosts() {

        return postRepository.findAll();
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

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
