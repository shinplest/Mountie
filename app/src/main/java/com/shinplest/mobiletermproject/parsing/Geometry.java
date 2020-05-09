package com.shinplest.mobiletermproject.parsing;

import java.util.ArrayList;

public class Geometry {
    private ArrayList<ArrayList<ArrayList<Double>>> paths;

    public ArrayList<ArrayList<ArrayList<Double>>> getPath() {
        return paths;
    }

    public void setPath(ArrayList<ArrayList<ArrayList<Double>>> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "path=" + paths +
                '}';
    }
}

