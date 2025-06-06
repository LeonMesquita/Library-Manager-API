package com.library_manager.api.services;

import com.library_manager.api.dtos.UserDTO;
import com.library_manager.api.exceptions.AuthorizationException;
import com.library_manager.api.exceptions.GenericConflictException;
import com.library_manager.api.exceptions.GenericNotFoundException;
import com.library_manager.api.models.UserModel;
import com.library_manager.api.models.enums.ProfileEnum;
import com.library_manager.api.repositories.UserRepository;
import com.library_manager.api.security.UserSpringSecurity;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserModel save(UserDTO dto) {
        this.existsByEmail(dto.getEmail());
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);
        userModel.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        userModel.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        return userRepository.save(userModel);
    }

    public void existsByEmail(String email) {
        boolean exists = userRepository.existsByEmail(email);
        if (exists) {
            throw new GenericConflictException("Já existe um usuário com o email " + email);
        }
    }

    public UserModel findById(Long id) {
        adminOrUserAuthenticated(id);
        return userRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("Usuário com o id " + id + " não encontrado!")
        );
    }


    public UserModel update(Long id, UserDTO dto) {
        UserModel user = this.findById(id);
    }


    public static void adminOrUserAuthenticated(Long id) {

        try {
            UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!Objects.nonNull(userSpringSecurity) || (!userSpringSecurity.hasRole(ProfileEnum.ADMIN)  && !id.equals(userSpringSecurity.getId())))
                throw new AuthorizationException("Acesso negado!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
