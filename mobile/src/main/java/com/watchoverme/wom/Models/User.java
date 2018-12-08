package com.watchoverme.wom.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("phone_num")
    @Expose
    private String phoneNum;
    @SerializedName("password")
    @Expose
    private String password;

    public User() {
    }

    public User(String phoneNum, String password) {
        this.phoneNum = phoneNum;
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
