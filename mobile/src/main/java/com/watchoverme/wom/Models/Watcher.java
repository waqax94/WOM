package com.watchoverme.wom.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Watcher {

    @SerializedName("wom_id")
    @Expose
    private String womId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("mobile_code")
    @Expose
    private String mobileCode;
    @SerializedName("mobile_num_privacy")
    @Expose
    private String mobileNumPrivacy;
    @SerializedName("priority")
    @Expose
    private String priority;

    public Watcher() {
    }

    public Watcher(String womId, String name, String mobile, String mobileCode, String mobileNumPrivacy, String priority) {
        this.womId = womId;
        this.name = name;
        this.mobile = mobile;
        this.mobileCode = mobileCode;
        this.mobileNumPrivacy = mobileNumPrivacy;
        this.priority = priority;
    }

    public String getWomId() {
        return womId;
    }

    public void setWomId(String womId) {
        this.womId = womId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getMobileNumPrivacy() {
        return mobileNumPrivacy;
    }

    public void setMobileNumPrivacy(String mobileNumPrivacy) {
        this.mobileNumPrivacy = mobileNumPrivacy;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
