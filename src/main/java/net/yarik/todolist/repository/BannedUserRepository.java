package net.yarik.todolist.repository;

import net.yarik.todolist.model.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {
    List<BannedUser> findByToken(String token);
}
