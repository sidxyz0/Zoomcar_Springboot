package com.example.zoomcar.shared.classes;

import java.util.Map;
public class RespBody {
    String status;
    String message;
    Map<String, Object> data;

    public RespBody() {
    }

    public RespBody(String status, String message, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
