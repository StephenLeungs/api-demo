package com.example.apidemo.service;

import com.example.apidemo.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, String> tokens = new HashMap<>();

    public static boolean register(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return false;
        }

        if (users.containsKey(username)) {
            return false;
        }

        User user = new User(username, password);
        users.put(username, user);
        return true;
    }

    public static String login(String username, String password) {
        User user = users.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        user.setToken(token);
        tokens.put(token, username);
        return token;
    }

    public static String checkAuth(String token) {
        return tokens.get(token);
    }
}