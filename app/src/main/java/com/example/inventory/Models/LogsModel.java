package com.example.inventory.Models;

public class LogsModel {

    public LogsModel(){}

    public LogsModel(String uname, String component, String datetime, int count,int logtype) {
        this.uname = uname;
        this.component = component;
        this.datetime = datetime;
        this.count = count;
        this.logtype =logtype;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLogtype() {
        return logtype;
    }

    public void setLogtype(int logtype) {
        this.logtype = logtype;
    }
    String uname,component,datetime;
    int count,logtype;
}