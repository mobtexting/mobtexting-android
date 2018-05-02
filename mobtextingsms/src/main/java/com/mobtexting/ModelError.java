package com.mobtexting;

public class ModelError {
    private String status;
    private String error;
    private String description;


    public ModelError(String status, String error, String description) {
        this.status = status;
        this.error = error;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
