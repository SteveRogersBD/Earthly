package com.example.earthly.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class YouTubeResponse {

    public String continuation;
    public String estimatedResults;
    public ArrayList<Datum> data;
    public String msg;
    public ArrayList<String> refinements;
    public class ChannelThumbnail{
        public String url;
        public int width;
        public int height;
    }

    public class Datum{
        public String type;
        public String videoId;
        public String title;
        public String channelTitle;
        public String channelId;
        public String channelHandle;
        public ArrayList<ChannelThumbnail> channelThumbnail;
        public String description;
        public String viewCount;
        public String publishedTimeText;
        public String publishDate;
        public Date publishedAt;
        public String lengthText;
        public ArrayList<Thumbnail> thumbnail;
        public ArrayList<RichThumbnail> richThumbnail;
        public ArrayList<Datum> data;
        public ArrayList<String> badges;
        public String viewCountText;
        public boolean isOriginalAspectRatio;
        public String params;
        public String playerParams;
        public String sequenceParams;
    }

    public class RichThumbnail{
        public String url;
        public int width;
        public int height;
    }

    public class Thumbnail{
        public String url;
        public int width;
        public int height;
    }
}
