package com.example.apidemo;

import com.example.apidemo.model.Response;
import com.example.apidemo.service.AuthService;
import com.example.apidemo.service.UserService;
import com.google.gson.Gson;
import spark.Spark;

import static spark.Spark.*;

public class Main {
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        // 设置端口
        port(4567);

        // 注册接口
        post("/register", (req, res) -> {
            res.type("application/json");

            String username = req.queryParams("username");
            String password = req.queryParams("password");
            String confirmPassword = req.queryParams("confirm_password");

            if (username == null || password == null || confirmPassword == null) {
                return gson.toJson(new Response(400, "参数不完整"));
            }

            boolean success = UserService.register(username, password, confirmPassword);

            if (success) {
                return gson.toJson(new Response(200, "注册成功"));
            } else {
                return gson.toJson(new Response(400, "注册失败，密码不一致或用户名已存在"));
            }
        });

        // 登录接口
        post("/login", (req, res) -> {
            res.type("application/json");

            String username = req.queryParams("username");
            String password = req.queryParams("password");

            if (username == null || password == null) {
                return gson.toJson(new Response(400, "参数不完整"));
            }

            String token = UserService.login(username, password);

            if (token != null) {
                return gson.toJson(new Response(200, "登录成功", token));
            } else {
                return gson.toJson(new Response(400, "登录失败，用户名或密码错误"));
            }
        });

        // 查看当前账号接口
        get("/check-username", (req, res) -> {
            String token = AuthService.getTokenFromHeader(req);

            if (token == null || !AuthService.validateToken(token)) {
                return "用户未登录";
            }

            String username = AuthService.getUsernameFromToken(token);
            return username;
        });

        // 等待服务器启动
        awaitInitialization();
        System.out.println("Server started on port 4567");
    }
}