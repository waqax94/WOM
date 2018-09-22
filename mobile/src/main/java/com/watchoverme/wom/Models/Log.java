package com.watchoverme.wom.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Log {

    @SerializedName("_id")
    @Expose
    private String logId;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("log_date")
    @Expose
    private String logDate;
    @SerializedName("log_time")
    @Expose
    private String logTime;
    @SerializedName("log_text")
    @Expose
    private String logText;
    @SerializedName("log_type")
    @Expose
    private String logType;
    @SerializedName("location_latitude")
    @Expose
    private String locationLatitude;
    @SerializedName("location_longitude")
    @Expose
    private String locationLongitude;
    @SerializedName("battery_percentage")
    @Expose
    private String batteryPercentage;
    @SerializedName("registration_token")
    @Expose
    private String registrationToken;
    @SerializedName("__v")
    @Expose
    private String versionInfo;

    public Log() {
    }

    public Log(String serviceId, String logDate, String logTime, String logText, String logType, String locationLatitude, String locationLongitude, String batteryPercentage, String registrationToken) {
        this.serviceId = serviceId;
        this.logDate = logDate;
        this.logTime = logTime;
        this.logText = logText;
        this.logType = logType;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.batteryPercentage = batteryPercentage;
        this.registrationToken = registrationToken;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(String batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }
}
