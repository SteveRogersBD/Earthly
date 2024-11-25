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
import com.example.earthly.apiIterfaces.VideoApiUtil;
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
    VideoApiUtil videoApiUtil;
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
        listItems.add(new ListItem("Conservation"));
        listItems.add(new ListItem("Renewable Energy"));
        listItems.add(new ListItem("Sustainability"));
        listItems.add(new ListItem("Climate Change"));
        listItems.add(new ListItem("Green House Gas"));

        textAdapter = new TextAdapter(this,listItems);
        binding.textRecycler.setAdapter(textAdapter);
        binding.textRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));


        //initializing the recyclerview with no videos
        videos = new ArrayList<>();
        videoAdapter = new VideoAdapter(MainActivity.this,videos);
        binding.videoRecycler.setAdapter(videoAdapter);
        binding.videoRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        updateVideos("plants sustainability nature");






    }

    public void updateVideos(String query){
        videoApiUtil = new VideoApiUtil();

        videoApiUtil.fetchVideos(query,
                getString(R.string.videoKey),
                getString(R.string.videoHost),
                new VideoApiUtil.VideoCallBack() {
                    @Override
                    public void onSuccess(List<YouTubeResponse.Datum> videoList) {
                        videos.clear();
                        videos.addAll(videoList);
                        videoAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}