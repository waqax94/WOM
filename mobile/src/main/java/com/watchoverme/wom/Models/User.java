package com.watchoverme.wom.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("wom_id")
    @Expose
    private String womId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("phone_code")
    @Expose
    private String phoneCode;
    @SerializedName("mobile_code")
    @Expose
    private String mobileCode;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("mobile_num_privacy")
    @Expose
    private String mobileNumPrivacy;
    @SerializedName("username")
    @Expose
    private String userName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("pharmacy")
    @Expose
    private String pharmacy;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("wearer")
    @Expose
    private String wearer;

    public User() {
    }

    public User(String womId, String name, String password, String email, String phone, String phoneCode, String mobileCode, String mobile, String mobileNumPrivacy, String userName, String status, String userType, String pharmacy, String customer, String wearer) {
        this.womId = womId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.phoneCode = phoneCode;
        this.mobileCode = mobileCode;
        this.mobile = mobile;
        this.mobileNumPrivacy = mobileNumPrivacy;
        this.userName = userName;
        this.status = status;
        this.userType = userType;
        this.pharmacy = pharmacy;
        this.customer = customer;
        this.wearer = wearer;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileNumPrivacy() {
        return mobileNumPrivacy;
    }

    public void setMobileNumPrivacy(String mobileNumPrivacy) {
        this.mobileNumPrivacy = mobileNumPrivacy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getWearer() {
        return wearer;
    }

    public void setWearer(String wearer) {
        this.wearer = wearer;
    }
}
