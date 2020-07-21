package ru.foxcrazy.simplecooker.service;

import ru.foxcrazy.simplecooker.domain.User;
import ru.foxcrazy.simplecooker.domain.enums.UserRoleEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepositoryService.getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User or password invalid");
        }

        Set<GrantedAuthority> roles = new HashSet();


        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPwHash(),
                roles);
    }

}
