
package com.shinplest.mobiletermproject.map.models.data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Page {

    @SerializedName("current")
    private String mCurrent;
    @SerializedName("size")
    private String mSize;
    @SerializedName("total")
    private String mTotal;

    public String getCurrent() {
        return mCurrent;
    }

    public void setCurrent(String current) {
        mCurrent = current;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

}
