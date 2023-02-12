package com.example.zoomcar.authentication;
public class AuthenticationModel {
    private String uuid;

    public AuthenticationModel(String uuid) {
        this.uuid = uuid;
    }

    public AuthenticationModel() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
