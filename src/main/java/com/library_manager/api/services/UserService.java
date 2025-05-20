package com.library_manager.api.services;

import com.library_manager.api.dtos.UserDTO;
import com.library_manager.api.models.UserModel;
import com.library_manager.api.models.enums.ProfileEnum;
import com.library_manager.api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public UserModel save(UserDTO dto) {
//        UserModel userModel = new UserModel();
//        BeanUtils.copyProperties(dto, userModel);
//        userModel.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
//        userModel.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
//        return userRepository.save(userModel);
//    }
}
