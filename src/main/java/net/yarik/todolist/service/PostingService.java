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

@Service
public class PostingService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    public List<Post> getAllPosts(Integer pageNumber) {
        Pageable pageOfSizeTen = PageRequest.of(pageNumber, 10);

        return postRepository.findAllPageable(pageOfSizeTen);
    }

    public List<Comment> getAllPostComments(Long postId, Integer pageNumber) {
        Pageable pageOfSizeTen = PageRequest.of(pageNumber, 10);

        return commentRepository.findByPostId(postId, pageOfSizeTen);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
