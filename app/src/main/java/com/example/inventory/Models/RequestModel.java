package com.example.inventory.Models;

public class RequestModel {
    String component;
    String requestcount;
    String uemail;
    String uname;
    String datetime;

    public RequestModel(){}

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRequestcount() {
        return requestcount;
    }

    public void setRequestcount(String requestcount) {
        this.requestcount = requestcount;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public RequestModel(String component, String requestcount, String uemail, String uname, String datetime) {
        this.component = component;
        this.requestcount = requestcount;
        this.uemail = uemail;
        this.uname = uname;
        this.datetime = datetime;
    }
}
