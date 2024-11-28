package com.example.earthly.apiIterfaces;

import android.content.Context;
import android.widget.Toast;

import com.example.earthly.R;
import com.example.earthly.RetrofitInstance;
import com.example.earthly.requests.EventRequest;
import com.example.earthly.responses.EventResponse;
import com.example.earthly.responses.MapResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapApiUtil {

    Context context;

    public MapApiUtil(Context context) {
        this.context = context;
    }

    public void searchLocations(Context context, String query, String latLong, MapCallBack callBack)
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

    //from here starts the event related api handler methods
    public void createEvent(int userId,EventRequest eventRequest)
    {
        RetrofitInstance.authApiInterface().createEvent(userId,eventRequest).enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                Toast.makeText(context, "Event created successfully!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable throwable) {
                Toast.makeText(context, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface MapCallBack{
        public List<MapResponse.LocalResult>onSuccess(List<MapResponse.LocalResult>results);
        public List<MapResponse.LocalResult>onFailure(String errorMessage);
    }

    public interface EventCallBack<T>{
        public T onSuccess(T data);
        public T onFailure(String errorMessage);
    }




}
