package com.watchoverme.wom.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhoneMeRequest {

    @SerializedName("recipient_num")
    @Expose
    private String recipientNum;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("service_id")
    @Expose
    private String serviceId;

    public PhoneMeRequest() {
    }

    public PhoneMeRequest(String recipientNum, String message, String serviceId) {
        this.recipientNum = recipientNum;
        this.message = message;
        this.serviceId = serviceId;
    }

    public String getRecipientNum() {
        return recipientNum;
    }

    public void setRecipientNum(String recipientNum) {
        this.recipientNum = recipientNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
