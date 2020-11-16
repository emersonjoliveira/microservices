package com.emersonoliveira.auth;

import com.emersonoliveira.auth.entity.Permission;
import com.emersonoliveira.auth.entity.User;
import com.emersonoliveira.auth.repository.PermissionRepository;
import com.emersonoliveira.auth.repository.UserRepository;
import java.util.Collections;
import java.util.Objects;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthApplication {

    public static final String USER_ADMIN = "admin";

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, PermissionRepository permissionRepository,
            BCryptPasswordEncoder passwordEncoder) {
        return args -> initUsers(userRepository, permissionRepository, passwordEncoder);
    }

    private void initUsers(UserRepository userRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder passwordEncoder) {
        Permission permissionAdmin = permissionRepository.findByDescription("Admin");
        if (Objects.isNull(permissionAdmin)) {
            permissionAdmin = new Permission();
            permissionAdmin.setDescription("Admin");
            permissionAdmin = permissionRepository.save(permissionAdmin);
        }

        User admin = new User();
        admin.setUserName(USER_ADMIN);
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);
        admin.setPassword(passwordEncoder.encode(USER_ADMIN));
        admin.setPermissions(Collections.singletonList(permissionAdmin));

        if (Objects.isNull(userRepository.findByUserName(USER_ADMIN))) {
            userRepository.save(admin);
        }
    }
}
