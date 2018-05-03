package com.mobtexting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponse {

    @Expose
    @SerializedName("sms_id")
    private List<String> smsId;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("error")
    private int error;
    @Expose
    @SerializedName("status")
    private String status;

    public List<String> getSmsId() {
        return smsId;
    }

    public void setSmsId(List<String> smsId) {
        this.smsId = smsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}