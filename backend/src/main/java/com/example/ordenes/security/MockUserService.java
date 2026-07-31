package com.example.ordenes.security;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MockUserService {


    private final Map<String, String> users = new HashMap<>();


    public MockUserService() {

        users.put("admin", "admin123");
        users.put("user", "user123");

    }


    public boolean validateUser(String username, String password) {

        return users.containsKey(username)
                && users.get(username).equals(password);
    }
}