package com.example.sofiane.googlemapproject.models;

import java.util.List;

/**
 * Created by Sofiane on 10/22/2018.
 */

public class ListLocation {

    private List<Location> mData;

    public ListLocation(List<Location> data) {
        mData = data;
    }

    public List<Location> getData() {
        return mData;
    }

    public void setData(List<Location> data) {
        mData = data;
    }
}
