package com.watchoverme.wom.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("wom_id")
    @Expose
    private String womId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("service_id")
    @Expose
    private String serviceId;

    public LoginResponse() {
    }

    public LoginResponse(String message, String womId, String userName, String password, String name, String serviceId) {
        this.message = message;
        this.womId = womId;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.serviceId = serviceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWomId() {
        return womId;
    }

    public void setWomId(String womId) {
        this.womId = womId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
