package com.shinplest.mobiletermproject.map;

import com.shinplest.mobiletermproject.parsing.Course;

import java.util.ArrayList;

public class ListViewItem {

    private String contentStr;
    private ArrayList<Course> CourseInfo;

    public void setContent(String content){
        contentStr = content;
    }

    public String getContent()
    {
        return this.contentStr;
    }


}
