package com.example.earthly.responses;

import java.util.Date;

public class EventResponse {

    public String status;
    public String message;
    public int statusCode;
    public Data data;
    public class CreatedBy{
        public int userId;
        public String username;
        public String email;
    }

    public class Data{
        public int eventId;
        public String title;
        public String description;
        public double latitude;
        public double longitude;
        public Date eventTime;
        public CreatedBy createdBy;
    }

}
