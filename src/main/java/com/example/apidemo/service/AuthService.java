package com.example.apidemo.service;

import spark.Request;

public class AuthService {
    public static String getTokenFromHeader(Request request) {
        return request.headers("auth");
    }

    public static boolean validateToken(String token) {
        return UserService.checkAuth(token) != null;
    }

    public static String getUsernameFromToken(String token) {
        return UserService.checkAuth(token);
    }
}