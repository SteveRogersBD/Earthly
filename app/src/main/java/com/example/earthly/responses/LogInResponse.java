package com.example.earthly.responses;

public class LogInResponse {
    public String status;
    public String message;
    public int statusCode;
    public Data data;
    public class Data{
        public int userId;
        public String username;
        public String email;
    }

}
