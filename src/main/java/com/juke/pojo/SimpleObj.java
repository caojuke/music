package com.juke.pojo;

import java.io.Serializable;

public class SimpleObj implements Serializable {
    private String name;

    public String getName() {
        return name;
    }
    public SimpleObj(){}
    public SimpleObj(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SimpleObj{" +
                "name='" + name + '\'' +
                '}';
    }
}
