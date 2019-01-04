package com.watchoverme.wom.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("_id")
    @Expose
    private String contactId;
    @SerializedName("watcherType")
    @Expose
    private String watcherType;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("watcherId")
    @Expose
    private String watcherId;
    @SerializedName("watcherName")
    @Expose
    private String watcherName;
    @SerializedName("watcherPhone")
    @Expose
    private String watcherPhone;

    public Contact() {
    }

    public Contact(String contactId, String watcherType, String priority, String watcherId, String watcherName, String watcherPhone) {
        this.contactId = contactId;
        this.watcherType = watcherType;
        this.priority = priority;
        this.watcherId = watcherId;
        this.watcherName = watcherName;
        this.watcherPhone = watcherPhone;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getWatcherType() {
        return watcherType;
    }

    public void setWatcherType(String watcherType) {
        this.watcherType = watcherType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getWatcherId() {
        return watcherId;
    }

    public void setWatcherId(String watcherId) {
        this.watcherId = watcherId;
    }

    public String getWatcherName() {
        return watcherName;
    }

    public void setWatcherName(String watcherName) {
        this.watcherName = watcherName;
    }

    public String getWatcherPhone() {
        return watcherPhone;
    }

    public void setWatcherPhone(String watcherPhone) {
        this.watcherPhone = watcherPhone;
    }
}
