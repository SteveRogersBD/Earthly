package com.example.earthly.apiIterfaces;

import com.example.earthly.RetrofitInstance;
import com.example.earthly.responses.YouTubeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VideoApiUtil {
    private static VideoApi videoApi = RetrofitInstance.VideoFit.create(VideoApi.class);
    public void fetchVideos(String query,String apiKey,String apiHost,VideoCallBack callBack )
    {
        Call<YouTubeResponse>call =  videoApi.searchVideos(query,apiKey,apiHost);
        call.enqueue(new Callback<YouTubeResponse>() {
            @Override
            public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                try{
                    if(response.isSuccessful() && response.body()!=null)
                    {
                        List<YouTubeResponse.Datum>videos = response.body().data;
                        ArrayList<YouTubeResponse.Datum> filtered = new ArrayList<>();
                        for(YouTubeResponse.Datum video: videos )
                        {
                            if(video.type.equals("video"))
                            {
                                filtered.add(video);
                            }
                        }
                        callBack.onSuccess(filtered);
                    }
                    else{
                        callBack.onError(response.message());
                    }

                }catch (Exception e)
                {
                    callBack.onError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<YouTubeResponse> call, Throwable throwable) {
                callBack.onError(throwable.getMessage());
            }
        });

    }

    public interface VideoCallBack{
        public void onSuccess(List<YouTubeResponse.Datum>videos);
        public void onError(String errorMessage);
    }
}
