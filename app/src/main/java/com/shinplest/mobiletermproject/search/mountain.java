package com.shinplest.mobiletermproject.search;

public class mountain {

    private String contentStr;
    private double TopLeft_lat;
    private double TopLeft_lng;
    private double RightBelow_lat;
    private double RightBelow_lng;

    public mountain(String name, double x1, double y1, double x2, double y2)
    {
        this.contentStr = name;
        this.TopLeft_lng = x1;
        this.TopLeft_lat = y1;
        this.RightBelow_lng = x2;
        this.RightBelow_lat = y2;
    }

    public void setContent(String content) {
        contentStr = content;
    }
    public void setTopLeft_lng(int x) {
        TopLeft_lng = x;
    }
    public void setTopLeft_lat(int y) {
        TopLeft_lat = y;
    }
    public void setRightBelow_lng(int x) {
        RightBelow_lng = x;
    }
    public void setRightBelow_lat(int y) {
        RightBelow_lat = y;
    }

    public String getContent() {
        return this.contentStr;
    }
    public double getTopLeft_lng() {
        return this.TopLeft_lng;
    }
    public double getTopLeft_lat() {
        return this.TopLeft_lat;
    }
    public double getRightBelow_lng() {
        return this.RightBelow_lng;
    }
    public double getRightBelow_lat() {
        return this.RightBelow_lat;
    }
}
