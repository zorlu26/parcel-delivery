package com.parceldelivery.parceluser.service;

import com.parceldelivery.parceluser.model.request.UserLoginRequest;
import com.parceldelivery.parceluser.model.entity.User;
import com.parceldelivery.parceluser.model.enums.Role;
import com.parceldelivery.parceluser.model.mapper.UserMapper;
import com.parceldelivery.parceluser.model.request.UserCourierCreateRequest;
import com.parceldelivery.parceluser.model.request.UserCreateRequest;
import com.parceldelivery.parceluser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public User createUser(UserCreateRequest userCreateRequest){
        final User user = UserMapper.INSTANCE.convertUserToUserCreateRequest(userCreateRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER.getValue());

        return userRepository.save(user);
    }

    public User login(UserLoginRequest userLoginRequest){
        final User user = userRepository.findByUsername(userLoginRequest.getUsername());
        boolean matches = bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword());

        if(matches){
            return user;
        }

        return null;
    }

    public User createCourier(Map<String, String> headers, UserCourierCreateRequest userCourierCreateRequest) throws Exception {
        String role = headers.get("role");

        if(!Role.ADMIN.getValue().equals(role)){
            throw new Exception("You are not authorized");
        }

        User user = UserMapper.INSTANCE.convertToModel(userCourierCreateRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN.getValue());

        return user;
    }
}
