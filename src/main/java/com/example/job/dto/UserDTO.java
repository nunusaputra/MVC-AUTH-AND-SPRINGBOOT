package com.example.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String password;
    private String fullname;
    private String nickname;
    private String phone;
    private String name;

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", fullname=" + fullname + ", nickname=" + nickname + ", phone=" + phone
                + ", name=" + name + ", password=" + password + "]";
    }

}
