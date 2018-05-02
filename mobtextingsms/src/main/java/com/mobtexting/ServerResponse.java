package com.mobtexting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServerResponse implements Serializable{

    @SerializedName("description")
    private String description;
    @SerializedName("error")
    private int error;
    @SerializedName("status")
    private String status;

    public ServerResponse(String description, int error, String status) {
        this.description = description;
        this.error = error;
        this.status = status;
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