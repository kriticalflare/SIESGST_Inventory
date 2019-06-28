package com.example.inventory.Models;

public class ComponentModel {

    int count;
    String components,date,category,admin;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public ComponentModel() {
    }

    public ComponentModel( String components, String date, String category,int count,String admin) {

        this.count = count;
        this.components = components;
        this.date = date;
        this.category = category;
        this.admin = admin;
    }
}
