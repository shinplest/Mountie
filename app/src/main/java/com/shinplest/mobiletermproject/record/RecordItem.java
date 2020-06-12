package com.shinplest.mobiletermproject.record;

import android.graphics.Bitmap;

public class RecordItem {
    private String record_txt;
    private Bitmap record_img;

    public String getRecord_txt() {
        return record_txt;
    }

    public void setRecord_txt(String record_txt) {
        this.record_txt = record_txt;
    }

    public Bitmap getRecord_img() {
        return record_img;
    }

    public void setRecord_img(Bitmap record_img) {
        this.record_img = record_img;
    }
}
