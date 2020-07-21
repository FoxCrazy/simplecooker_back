package ru.foxcrazy.simplecooker.controller;

import ru.foxcrazy.simplecooker.domain.User;
import ru.foxcrazy.simplecooker.service.UserDetailsServiceImpl;
import ru.foxcrazy.simplecooker.service.UserRepositoryService;
import ru.foxcrazy.simplecooker.token.JwtResponse;
import ru.foxcrazy.simplecooker.token.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping
@CrossOrigin(origins = "*", maxAge = 4800, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT}, allowedHeaders = "*")
public class RegistrationController {

    @Autowired
    private UserRepositoryService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;



    @PostMapping(path = "/registration")
    public ResponseEntity<?> addPerson(@RequestBody User user) {
        user.setCreatedAt(new Date());
        userService.saveUser(user);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);


        return ResponseEntity.ok(new JwtResponse(token));
    }
}
