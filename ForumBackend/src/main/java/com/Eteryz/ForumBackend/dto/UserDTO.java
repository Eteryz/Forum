package com.Eteryz.ForumBackend.dto;

import com.Eteryz.ForumBackend.entity.Role;
import com.Eteryz.ForumBackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    private String id;
    private String name;
    @NotBlank
    @NotNull
    private String username;
    @Email
    private String email;
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$")
    private String phone;
    private byte[] avatar;
    private String location;
    private boolean deleted;
    private Set<String> roles = new HashSet<>();

    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setRoles(
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .map(Enum::name)
                        .collect(Collectors.toSet()));
        return userDTO;
    }

    public User toEntity(User user) {
        BeanUtils.copyProperties(this, user);
        return user;
    }


}
