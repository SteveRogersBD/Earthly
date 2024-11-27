package com.example.earthly.apiIterfaces;

import android.content.Context;
import android.widget.Toast;

import com.example.earthly.R;
import com.example.earthly.RetrofitInstance;
import com.example.earthly.responses.MapResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapApiUtil {

    public void searchLocations(Context context,String query, String latLong,MapCallBack callBack)
    {
        String apiKey = context.getString(R.string.map_key);
        MapApiInterface mapApi = RetrofitInstance.createMapApi();
        Call<MapResponse>call = mapApi.getParkLocations(apiKey,
                                                        "google_maps",
                                                        query,latLong);
        call.enqueue(new Callback<MapResponse>() {
            @Override
            public void onResponse(Call<MapResponse> call, Response<MapResponse> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    try{
                        List<MapResponse.LocalResult> result = response.body().local_results;
                        callBack.onSuccess(result);

                    }catch (Exception e)
                    {
                        callBack.onFailure(e.getLocalizedMessage());
                    }
                }
                else{
                    callBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<MapResponse> call, Throwable throwable) {
                callBack.onFailure(throwable.getLocalizedMessage());
            }
        });
    }

    public interface MapCallBack{
        public List<MapResponse.LocalResult>onSuccess(List<MapResponse.LocalResult>results);
        public List<MapResponse.LocalResult>onFailure(String errorMessage);
    }
}
