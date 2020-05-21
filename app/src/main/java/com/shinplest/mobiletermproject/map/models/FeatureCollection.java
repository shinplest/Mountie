
package com.shinplest.mobiletermproject.map.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class FeatureCollection {

    @SerializedName("bbox")
    private List<Double> mBbox;
    @SerializedName("features")
    private List<Feature> mFeatures;
    @SerializedName("type")
    private String mType;

    public List<Double> getBbox() {
        return mBbox;
    }

    public void setBbox(List<Double> bbox) {
        mBbox = bbox;
    }

    public List<Feature> getFeatures() {
        return mFeatures;
    }

    public void setFeatures(List<Feature> features) {
        mFeatures = features;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
