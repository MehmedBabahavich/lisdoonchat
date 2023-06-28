package net.yarik.todolist.service;

import net.yarik.todolist.model.Comment;
import net.yarik.todolist.model.Post;
import net.yarik.todolist.repository.CommentRepository;
import net.yarik.todolist.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Comment> getAllPostComments(Long postId) {
        return commentRepository.findByPostId(postId); // ?
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public void createComment() {}
}
