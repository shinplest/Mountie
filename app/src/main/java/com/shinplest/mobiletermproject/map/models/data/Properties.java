
package com.shinplest.mobiletermproject.map.models.data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Properties {

    //난이도 상,중,하
    @SerializedName("cat_nam")
    private String mCatNam;
    //하행속도 분
    @SerializedName("down_min")
    private String mDownMin;
    //종점 표고 m
    @SerializedName("end_z")
    private String mEndZ;
    //구간거리 m
    @SerializedName("sec_len")
    private String mSecLen;
    //시점 표고 m
    @SerializedName("start_z")
    private String mStartZ;
    //상행속도 분
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
