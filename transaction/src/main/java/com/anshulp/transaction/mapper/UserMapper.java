package com.anshulp.transaction.mapper;


import com.anshulp.transaction.dto.UserResponse;
import com.anshulp.transaction.entity.User;

public class UserMapper {

    public static UserResponse toDto(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }

     public User toEntity(UserResponse response) {
        com.anshulp.transaction.entity.User user = new com.anshulp.transaction.entity.User();
        user.setId(response.getId());
        user.setName(response.getName());
        user.setEmail(response.getEmail());
        return user;
    }
}
