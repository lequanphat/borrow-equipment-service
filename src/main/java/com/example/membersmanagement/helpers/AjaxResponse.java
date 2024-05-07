package com.example.membersmanagement.helpers;

import lombok.Data;

@Data
public class AjaxResponse {
    private String message;
    private Object data;

    public AjaxResponse(String message) {
        this.message = message;
    }

    public AjaxResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
