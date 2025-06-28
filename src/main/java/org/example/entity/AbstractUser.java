package org.example.entity;

import lombok.Data;

@Data
public abstract class AbstractUser {
    String userName;
    String password;
    int userId;
}
