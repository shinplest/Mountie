
package com.shinplest.mobiletermproject.map.models.data;

import com.google.gson.annotations.SerializedName;
import com.shinplest.mobiletermproject.map.models.data.FeatureCollection;

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
