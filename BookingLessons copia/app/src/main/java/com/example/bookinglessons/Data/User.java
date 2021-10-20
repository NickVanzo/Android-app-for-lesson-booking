package com.example.bookinglessons.Data;

import java.io.Serializable;

public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }
}
