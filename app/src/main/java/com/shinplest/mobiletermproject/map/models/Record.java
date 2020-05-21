
package com.shinplest.mobiletermproject.map.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Record {

    @SerializedName("current")
    private String mCurrent;
    @SerializedName("total")
    private String mTotal;

    public String getCurrent() {
        return mCurrent;
    }

    public void setCurrent(String current) {
        mCurrent = current;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

}
