package com.example.earthly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.earthly.adapters.TextAdapter;
import com.example.earthly.adapters.VideoAdapter;
import com.example.earthly.apiIterfaces.VideoApi;
import com.example.earthly.databinding.ActivityMainBinding;
import com.example.earthly.models.ListItem;
import com.example.earthly.responses.YouTubeResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TextAdapter textAdapter;
    VideoAdapter videoAdapter;
    VideoApi videoApi;
    List<YouTubeResponse.Datum> videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the background drawable (gradient)
        GradientDrawable gradientDrawable = (GradientDrawable) getResources().
                getDrawable(R.drawable.onboard_bg);

        // Extract the start color from the gradient (you can also use the end color if you prefer)
        int startColor = gradientDrawable.getColors()[0];  // First color of the gradient

        // Set the status bar color to match the gradient start color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(startColor);  // Set status bar color to the start color of the gradient

        List<ListItem>listItems = new ArrayList<>();
        listItems.add(new ListItem("All"));
        listItems.add(new ListItem("Trending"));
        listItems.add(new ListItem("Care"));
        listItems.add(new ListItem("Sustainability"));

        textAdapter = new TextAdapter(this,listItems);
        binding.textRecycler.setAdapter(textAdapter);
        binding.textRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));


        videoApi = RetrofitInstance.VideoFit.create(VideoApi.class);
        Call<YouTubeResponse>getVideos = videoApi.searchVideos("Chemistry experiments",
                getString(R.string.videoKey),
                getString(R.string.videoHost));
        getVideos.enqueue(new Callback<YouTubeResponse>() {
            @Override
            public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                try{
                    if (response.body() != null && response.isSuccessful()) {
                        // Populate the videos list with data from the response
                        videos = response.body().data;
                        ArrayList<YouTubeResponse.Datum>filtered = new ArrayList<>();
                        for(YouTubeResponse.Datum video: videos )
                        {
                            if(video.type.equals("video"))
                            {
                             filtered.add(video);
                            }
                        }
                        videoAdapter = new VideoAdapter(MainActivity.this, filtered);
                        binding.videoRecycler.setAdapter(videoAdapter);
                        binding.videoRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                                LinearLayoutManager.HORIZONTAL, false));
                    } else {
                        // Handle empty response
                        Toast.makeText(MainActivity.this, "No videos found", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<YouTubeResponse> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });






    }
}