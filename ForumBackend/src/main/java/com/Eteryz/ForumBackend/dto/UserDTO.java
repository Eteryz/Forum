package com.Eteryz.ForumBackend.dto;

import com.Eteryz.ForumBackend.models.ERole;
import com.Eteryz.ForumBackend.models.Role;
import com.Eteryz.ForumBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.IOException;
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
    private String city;
    private Set<String> roles;

    public static UserDTO toModel(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setRoles(user.getRoles().stream()
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
