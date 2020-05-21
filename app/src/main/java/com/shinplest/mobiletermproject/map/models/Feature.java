
package com.shinplest.mobiletermproject.map.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Feature {

    @SerializedName("geometry")
    private Geometry mGeometry;
    @SerializedName("id")
    private String mId;
    @SerializedName("properties")
    private Properties mProperties;
    @SerializedName("type")
    private String mType;

    public Geometry getGeometry() {
        return mGeometry;
    }

    public void setGeometry(Geometry geometry) {
        mGeometry = geometry;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Properties getProperties() {
        return mProperties;
    }

    public void setProperties(Properties properties) {
        mProperties = properties;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
