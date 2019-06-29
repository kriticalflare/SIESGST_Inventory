package com.example.inventory.Models;

public class ComponentModel {
    public ComponentModel(){}

    public ComponentModel(String component, String adder, int count) {
        this.component = component;
        this.adder = adder;
        this.count = count;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    String component,adder;
    int count;
}
