package com.shinplest.mobiletermproject.record;

import android.graphics.Bitmap;

import java.util.Date;

public class RecordItem {
    private String recordTxt;
    private Bitmap recordImg;
    private String maxAltitude;
    private String avgSpeed;
    private String totalDistance;
    private String time;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRecord_txt() {
        return recordTxt;
    }

    public void setRecord_txt(String recordTxt) {
        this.recordTxt = recordTxt;
    }

    public Bitmap getRecord_img() {
        return recordImg;
    }

    public void setRecord_img(Bitmap recordImg) {
        this.recordImg = recordImg;
    }

    public String getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(String maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public String getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(String avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
