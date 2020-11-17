package com.techdenovo.rolebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String emailId;
    private String username;
    private String password;
    private String role;
    private String designation;
}
