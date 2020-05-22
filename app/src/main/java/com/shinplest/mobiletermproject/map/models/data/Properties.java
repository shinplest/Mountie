
package com.shinplest.mobiletermproject.map.models.data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Properties {

    @SerializedName("cat_nam")
    private String mCatNam;
    @SerializedName("down_min")
    private String mDownMin;
    @SerializedName("end_z")
    private String mEndZ;
    @SerializedName("sec_len")
    private String mSecLen;
    @SerializedName("start_z")
    private String mStartZ;
    @SerializedName("up_min")
    private String mUpMin;

    public String getCatNam() {
        return mCatNam;
    }

    public void setCatNam(String catNam) {
        mCatNam = catNam;
    }

    public String getDownMin() {
        return mDownMin;
    }

    public void setDownMin(String downMin) {
        mDownMin = downMin;
    }

    public String getEndZ() {
        return mEndZ;
    }

    public void setEndZ(String endZ) {
        mEndZ = endZ;
    }

    public String getSecLen() {
        return mSecLen;
    }

    public void setSecLen(String secLen) {
        mSecLen = secLen;
    }

    public String getStartZ() {
        return mStartZ;
    }

    public void setStartZ(String startZ) {
        mStartZ = startZ;
    }

    public String getUpMin() {
        return mUpMin;
    }

    public void setUpMin(String upMin) {
        mUpMin = upMin;
    }

}
