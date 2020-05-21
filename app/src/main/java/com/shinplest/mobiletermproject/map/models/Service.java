
package com.shinplest.mobiletermproject.map.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Service {

    @SerializedName("name")
    private String mName;
    @SerializedName("operation")
    private String mOperation;
    @SerializedName("time")
    private String mTime;
    @SerializedName("version")
    private String mVersion;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOperation() {
        return mOperation;
    }

    public void setOperation(String operation) {
        mOperation = operation;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

}
