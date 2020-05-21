
package com.shinplest.mobiletermproject.map.models.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Geometry {

    @SerializedName("coordinates")
    private List<List<List<Double>>> mCoordinates;
    @SerializedName("type")
    private String mType;

    public List<List<List<Double>>> getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        mCoordinates = coordinates;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
