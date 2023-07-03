package net.yarik.todolist.repository;

import net.yarik.todolist.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllPageable(Pageable pageable);
    List<Post> findByImageName(String imageName);
}
