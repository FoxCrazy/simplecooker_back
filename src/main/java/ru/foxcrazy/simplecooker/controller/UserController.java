package ru.foxcrazy.simplecooker.controller;


import ru.foxcrazy.simplecooker.domain.User;
import ru.foxcrazy.simplecooker.token.JwtTokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.foxcrazy.simplecooker.service.UserRepositoryService;

@RestController

@RequestMapping
public class UserController {
    private UserRepositoryService userRepositoryService;

    public UserController(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

}
