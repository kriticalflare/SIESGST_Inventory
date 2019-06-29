package com.example.inventory.Models;

public class LoginModel {
    public LoginModel(String email, String status) {
        this.email = email;
        this.status = status;
    }
    public  LoginModel(){}

    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

}
