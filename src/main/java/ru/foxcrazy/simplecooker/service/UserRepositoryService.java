package ru.foxcrazy.simplecooker.service;

import ru.foxcrazy.simplecooker.domain.User;
import ru.foxcrazy.simplecooker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@Service
public class UserRepositoryService{

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepo repository;
    @Autowired
    public UserRepositoryService(UserRepo repository) {
        this.repository = repository;
    }

    public User getUserByLogin(String login) {
        User user = repository.getUserByEmail(login);
        //System.out.println(user.getEmail());
        return user;
    }

    public List<User> getAll() {

        return repository.findAll();
    }

    public User getUser(String login) {
        return this.repository.findAllByEmail(login);
    }

    public boolean saveUser(User user) {
        User userFromDB = repository.getUserByEmail(user.getEmail());

        if (userFromDB != null) {
            return false;
        }

        user.setPwHash(new BCryptPasswordEncoder().encode(user.getPwHash()));
        repository.save(user);
        return true;
    }

    public String getFirstNameByLogin(String login) {
        return this.getUserByLogin(login).getFullName();
    }

}
