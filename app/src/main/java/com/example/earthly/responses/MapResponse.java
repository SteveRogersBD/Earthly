package com.example.earthly.responses;

import java.util.ArrayList;

public class MapResponse {
    public SearchMetadata search_metadata;
    public SearchParameters search_parameters;
    public SearchInformation search_information;
    public ArrayList<LocalResult> local_results;
    public SerpapiPagination serpapi_pagination;
    public class GpsCoordinates{
        public double latitude;
        public double longitude;
    }

    public class LocalResult{
        public int position;
        public String title;
        public String place_id;
        public String data_id;
        public String data_cid;
        public String reviews_link;
        public String photos_link;
        public GpsCoordinates gps_coordinates;
        public String place_id_search;
        public String provider_id;
        public double rating;
        public int reviews;
        public boolean unclaimed_listing;
        public String type;
        public ArrayList<String> types;
        public String type_id;
        public ArrayList<String> type_ids;
        public String address;
        public String open_state;
        public String hours;
        public OperatingHours operating_hours;
        public String thumbnail;
        public String phone;
        public String description;
        public String website;
    }

    public class OperatingHours{
        public String wednesday;
        public String thursday;
        public String friday;
        public String saturday;
        public String sunday;
        public String monday;
        public String tuesday;
    }

    public class SearchInformation{
        public String local_results_state;
        public String query_displayed;
    }

    public class SearchMetadata{
        public String id;
        public String status;
        public String json_endpoint;
        public String created_at;
        public String processed_at;
        public String google_maps_url;
        public String raw_html_file;
        public double total_time_taken;
    }

    public class SearchParameters{
        public String engine;
        public String type;
        public String q;
        public String ll;
        public String google_domain;
        public String hl;
    }

    public class SerpapiPagination{
        public String next;
    }

}
