package com.Inova.ClickupDashboard.exception.impl;

import com.Inova.ClickupDashboard.controllers.App_constants;
import com.Inova.ClickupDashboard.exception.BaseException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppsRuntimeException extends RuntimeException implements Serializable, BaseException {

    private static final long serialVersionUID = -1224752213086766591L;



    private final int INTERNAL_SERVER_ERROR = 500;
    private final int BAD_REQUEST = 400;
    private final int HTTP_OK = 200;

    private List<AppsErrorMessage> appsErrorMessages = new ArrayList<>();
    private int httpStatus = HTTP_OK;
    private App_constants.ResponseStatus responseStatus = App_constants.ResponseStatus.FAILED;

    public AppsRuntimeException() {
    }

    public AppsRuntimeException(Throwable cause, String errorCode, String errorMessage, App_constants.ResponseStatus responseStatus) {
        super(errorCode, cause);
        this.appsErrorMessages.add(new AppsErrorMessage(errorCode, errorMessage));
        this.httpStatus = httpStatus;
        this.responseStatus = responseStatus;
    }

    public AppsRuntimeException(String errorCode, String errorMessage, App_constants.ResponseStatus responseStatus) {
        super(errorCode);
        this.appsErrorMessages.add(new AppsErrorMessage(errorCode, errorMessage));
        this.responseStatus = responseStatus;
    }

    public AppsRuntimeException(String errorCode, App_constants.ResponseStatus responseStatus) {
        super(errorCode);
        this.appsErrorMessages.add(new AppsErrorMessage(errorCode));
        this.responseStatus = responseStatus;
    }

    public AppsRuntimeException(String errorCode) {
        super(errorCode);
        this.appsErrorMessages.add(new AppsErrorMessage(errorCode));
        this.responseStatus = App_constants.ResponseStatus.FAILED;
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

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    public void markExceptionAsInternalServerError() {
        this.httpStatus = this.INTERNAL_SERVER_ERROR;
    }

    @Override
    public App_constants.ResponseStatus getResponseStatus() {
        return responseStatus;
    }


    public void setResponseStatus(App_constants.ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public void setBadRequest() {
        this.httpStatus = BAD_REQUEST;
    }

    @Override
    public Boolean containsErrorCode(String errorCode) {
        for (AppsErrorMessage appsErrorMessage : appsErrorMessages) {
            if (appsErrorMessage.getErrorCode() == errorCode) {
                return true;
            }
        }
        return false;
    }
}
