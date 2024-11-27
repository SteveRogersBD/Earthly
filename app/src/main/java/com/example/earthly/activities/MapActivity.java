package com.example.earthly.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.earthly.R;
import com.example.earthly.apiIterfaces.MapApiUtil;
import com.example.earthly.databinding.ActivityMapBinding;
import com.example.earthly.responses.MapResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Dialog dialog;
    ActivityMapBinding binding;
    MapApiUtil mapApiUtil;
    Double myLatitude;
    Double myLongitude;
    SharedPreferences locationPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationPreference = getApplicationContext().getSharedPreferences("Location",
                Context.MODE_PRIVATE);
        mapApiUtil = new MapApiUtil();
        useMapApiUtil("Parks near me");
        useMapApiUtil("Farmer's market near me");
        useMapApiUtil("Eco near me");
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        binding.addButton.setOnClickListener((v)->{

        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        checkLocationPermission();


    }
    // Check if location permission is granted
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, get the location
            getCurrentLocation();
        }
    }
    private void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        LatLng myLocation = retrieveLocation();
        if(myLocation!=null)
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 100)); // Adjusted zoom level to 17

            // Add a marker at the user's location
            mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; // You can also request permissions here
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        storeInSharedPreference(latitude,longitude);
                        // Create a LatLng object for the current location
                        LatLng userLocation = new LatLng(latitude, longitude);

                        // Move the camera to the user's location with a closer zoom
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 5)); // Adjusted zoom level to 17

                        // Add a marker at the user's location
                        mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location"));
                    } else {
                        // Handle case where location is null
                        Log.d("Location", "Location is null");
                    }
                });
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Permission was denied!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to search for a location
    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(location, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                // Move the camera to the new location
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                // Add a marker at the new location
                mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
                mMap.addCircle(new CircleOptions()
                        .center(latLng) // Set the center to the searched location
                        .radius(100) // Radius in meters (adjust as needed)
                        .strokeColor(0xFF0000FF) // Blue outline
                        .fillColor(0x220000FF) // Light blue fill
                        .strokeWidth(5)); // Width of the circle outline
            } else {
                Log.d("SearchLocation", "Location not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SearchLocation", "Geocoder service not available");
        }
    }
    private void useMapApiUtil(String query)
    {

        LatLng myLocation = retrieveLocation();
        double lat = myLocation.latitude;
        double lon = myLocation.longitude;
        String location = "@"+lat+","+lon+","+"14z";
        mapApiUtil.searchLocations(getApplicationContext(), query, location, new MapApiUtil.MapCallBack() {
            @Override
            public List<MapResponse.LocalResult> onSuccess(List<MapResponse.LocalResult> results) {

                drawMarker(results);
                return null;
            }

            @Override
            public List<MapResponse.LocalResult> onFailure(String errorMessage) {
                return null;
            }
        });
    }
    private void storeInSharedPreference(Double lat,Double longi)
    {
        SharedPreferences.Editor editor = locationPreference.edit();
        editor.putString("Latitude",lat+"");
        editor.putString("Longitude",longi+"");
        editor.apply();
    }
    private LatLng retrieveLocation()
    {
        String latString = locationPreference.getString("Latitude",null);
        String lonString = locationPreference.getString("Longitude",null);
        if(latString==null || lonString==null)
        {
            Toast.makeText(this,"Location not found",Toast.LENGTH_LONG).show();
            return null;
        }
        try{
            myLatitude = Double.valueOf(locationPreference.getString("Latitude",null));
            myLongitude = Double.valueOf(locationPreference.getString("Longitude",null));
            LatLng myLocation= new LatLng(myLatitude,myLongitude);
            return myLocation;
        }catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }

    }
    private void drawMarker(List<MapResponse.LocalResult>results)
    {
        for(MapResponse.LocalResult add:results)
        {
            LatLng location = new LatLng(add.gps_coordinates.latitude,add.gps_coordinates.longitude);
            mMap.addMarker(new MarkerOptions().position(location).title(add.title));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }

}