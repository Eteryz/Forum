package com.Eteryz.ForumBackend.model;

import com.Eteryz.ForumBackend.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String login;
    private String email;
    private String phone;
    private String avatar;
    private String city;


    public static User toModel(UserEntity userEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    public UserEntity toEntity() {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(this, user);
        return user;
    }


}
