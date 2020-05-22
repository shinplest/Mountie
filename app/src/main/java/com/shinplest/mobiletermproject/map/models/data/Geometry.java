
package com.shinplest.mobiletermproject.map.models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Geometry {

    @SerializedName("coordinates")
    private ArrayList<ArrayList<ArrayList<Double>>> mCoordinates;
    @SerializedName("type")
    private String mType;

    public ArrayList<ArrayList<ArrayList<Double>>> getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<ArrayList<Double>>> coordinates) {
        mCoordinates = coordinates;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
