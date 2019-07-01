package com.example.inventory.Models;

public class FinalRequestModel {
    String component;
    String requestcount;
    String uemail;
    String uname;
    String datetime;
    String requesttype;

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public FinalRequestModel() {}

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public FinalRequestModel(String component, String requestcount, String uemail, String uname, String datetime, Integer count,String requesttype) {
        this.component = component;
        this.requestcount = requestcount;
        this.uemail = uemail;
        this.uname = uname;
        this.datetime = datetime;
        this.count = count;
        this.requesttype =requesttype;
    }

    Integer count;
}
