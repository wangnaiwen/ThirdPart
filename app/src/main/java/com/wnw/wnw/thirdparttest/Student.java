package com.wnw.wnw.thirdparttest;

import java.io.Serializable;
import java.util.List;

/**
 * @author wnw
 * @date 2017/11/21 0021 14:01
 */
public class Student implements Serializable {
    private int id;
    private String name;
    private List<Course> courses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
