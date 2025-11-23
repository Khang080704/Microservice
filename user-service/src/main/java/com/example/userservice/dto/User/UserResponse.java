package com.example.userservice.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {
    private UUID Id;
    private String fullName;
    private String phoneNumber;
    private String address;
}
