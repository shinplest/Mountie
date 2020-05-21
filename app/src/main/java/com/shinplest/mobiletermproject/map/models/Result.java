
package com.shinplest.mobiletermproject.map.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Result {

    @SerializedName("featureCollection")
    private FeatureCollection mFeatureCollection;

    public FeatureCollection getFeatureCollection() {
        return mFeatureCollection;
    }

    public void setFeatureCollection(FeatureCollection featureCollection) {
        mFeatureCollection = featureCollection;
    }

}
