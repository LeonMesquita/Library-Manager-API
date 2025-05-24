package com.library_manager.api.configs;

import com.library_manager.api.models.UserModel;
import com.library_manager.api.models.enums.ProfileEnum;
import com.library_manager.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AdminUserInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void initAdminUser() {
        String defaultAdminUsername = "admin@gmail.com";

        if (!userRepository.existsByEmail(defaultAdminUsername)) {
            UserModel admin = new UserModel();
            admin.setName("admin");
            admin.setEmail(defaultAdminUsername);
            admin.setPassword(bCryptPasswordEncoder.encode("new@admin")); // escolha uma senha segura
            admin.setProfiles(Stream.of(ProfileEnum.ADMIN.getCode()).collect(Collectors.toSet()));
            userRepository.save(admin);

            System.out.println("Usuário ADMIN padrão criado: admin / admin123");
        }
    }
}
