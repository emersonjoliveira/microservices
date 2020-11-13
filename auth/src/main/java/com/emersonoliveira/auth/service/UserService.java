package com.emersonoliveira.auth.service;

import com.emersonoliveira.auth.repository.UserRepository;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        var user = repository.findByUserName(userName);
        if (Objects.nonNull(user)) {
            return user;
        }
        throw new UsernameNotFoundException(String.format("Username %s not found.", userName));
    }
}
