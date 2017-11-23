package com.wnw.wnw.thirdparttest;

import java.io.Serializable;

/**
 * @author wnw
 * @date 2017/11/21 0021 14:28
 */
public class Course implements Serializable{
    private int cId;
    private String name;

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
