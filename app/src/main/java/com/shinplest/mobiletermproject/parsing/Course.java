package com.shinplest.mobiletermproject.parsing;

import java.util.ArrayList;

public class Course {
    private Attributes attributes;
    private Geometry geometry;
    private ArrayList<Object> course;

    public Course() {

    }

    public Course(Attributes attributes, Geometry geometry) {
        this.attributes = attributes;
        this.geometry = geometry;
    }

    public ArrayList<Object> getCourse() {
        return course;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
