package com.example.sofiane.googlemapproject.networking;

import com.example.sofiane.googlemapproject.models.ListLocation;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sofiane on 10/22/2018.
 */

public interface ApiService {
    @GET("api/v1/parkinglocations")
    Call<ListLocation> getAllLocation();
}
