package net.yarik.todolist.service;

import net.yarik.todolist.exceptions.UserDoesNotExist;
import net.yarik.todolist.model.BannedUser;
import net.yarik.todolist.repository.BannedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanService {

    @Autowired
    private BannedUserRepository bannedUserRepository;

    public void banUser(String userToken) {
        BannedUser bu = new BannedUser();
        bu.setToken(userToken);

        bannedUserRepository.save(bu);
    }

    public void unbanUser(String userToken) throws UserDoesNotExist {
        if (bannedUserRepository.findByToken(userToken).isEmpty()) {
            throw new UserDoesNotExist();
        }

        BannedUser bu = new BannedUser();
        bu.setToken(userToken);

        bannedUserRepository.save(bu);
    }
}
