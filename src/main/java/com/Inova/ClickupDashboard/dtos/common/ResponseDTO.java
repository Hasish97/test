package com.Inova.ClickupDashboard.dtos.common;

import com.Inova.ClickupDashboard.controllers.App_constants;
import com.Inova.ClickupDashboard.exception.impl.AppsErrorMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = -726587114041152869L;

    private List<AppsErrorMessage> appsErrorMessages = new ArrayList<>();
    private T result;
    private App_constants.ResponseStatus status;

    public ResponseDTO() {
    }

    public List<AppsErrorMessage> getAppsErrorMessages() {
        return appsErrorMessages;
    }

    public void setAppsErrorMessages(List<AppsErrorMessage> appsErrorMessages) {
        this.appsErrorMessages = appsErrorMessages;
    }

    public void addError(String errorCode) {
        this.appsErrorMessages.add(new AppsErrorMessage(errorCode));
    }

    public void addError(String errorCode, String errorMessage) {
        this.appsErrorMessages.add(new AppsErrorMessage(errorCode, errorMessage));
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public App_constants.ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(App_constants.ResponseStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "appsErrorMessages=" + appsErrorMessages +
                ", result=" + result +
                ", status=" + status +
                '}';
    }
}
