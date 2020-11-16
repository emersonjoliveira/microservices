package com.emersonoliveira.auth.controller;

import com.emersonoliveira.auth.entity.User;
import com.emersonoliveira.auth.jwt.JwtTokenProvider;
import com.emersonoliveira.auth.repository.UserRepository;
import com.emersonoliveira.auth.service.data.vo.UserVO;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider provider;
    private final UserRepository repository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider provider,
            UserRepository repository) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.repository = repository;
    }

    @RequestMapping("/testeSecurity")
    public String teste() {
        return "testado";
    }

    @PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
            "application/json", "application/xml", "application/x-yaml" })
    public ResponseEntity<?> login(@RequestBody UserVO vo) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(vo.getUserName(), vo.getPassword()));

            Map<Object, Object> model = new HashMap<>();
            model.put("username", vo.getUserName());
            model.put("token", getToken(vo));

            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password.");
        }
    }

    private String getToken(UserVO vo) {
        final User user = repository.findByUserName(vo.getUserName());
        if (Objects.nonNull(user)) {
            return provider.createToken(vo.getUserName(), user.getRoles());
        }
        throw new UsernameNotFoundException("Username not found!");
    }
}
