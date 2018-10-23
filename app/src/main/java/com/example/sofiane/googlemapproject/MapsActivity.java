package com.example.sofiane.googlemapproject;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sofiane.googlemapproject.models.ListLocation;
import com.example.sofiane.googlemapproject.models.Location;
import com.example.sofiane.googlemapproject.networking.ApiClient;
import com.example.sofiane.googlemapproject.networking.ApiService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Location> mListMarker = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        getAllDataLocationLatLng();
    }

    private void getAllDataLocationLatLng(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ListLocation> call = apiService.getAllLocation();
        call.enqueue(new Callback<ListLocation>() {
            @Override
            public void onResponse(Call<ListLocation> call, Response<ListLocation> response) {
                dialog.dismiss();
                mListMarker = response.body().getData();
                initMarker(mListMarker);
            }

            @Override
            public void onFailure(Call<ListLocation> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initMarker(List<Location> listData){

        for (int i=0; i<listData.size(); i++){

            LatLng location = new LatLng(Double.parseDouble(listData.get(i).getLat()), Double.parseDouble(listData.get(i).getLng()));

            mMap.addMarker(new MarkerOptions().position(location).title(listData.get(i).getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

            //LatLng latLng = new LatLng(Double.parseDouble(mListMarker.get(0).getLat()), Double.parseDouble(mListMarker.get(0).getLng()));


            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude), 11.0f));

           // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}
