package net.yarik.todolist.repository;

import net.yarik.todolist.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.lastBump DESC")
    List<Post> findAllOrderByLastBumpDesc();
    List<Post> findAll();
    Optional<Post> findById(Long id);
    List<Post> findByImageName(String imageName);
}
