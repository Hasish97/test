package com.Inova.ClickupDashboard.exception;

import com.Inova.ClickupDashboard.controllers.App_constants;
import com.Inova.ClickupDashboard.exception.impl.AppsErrorMessage;

import java.util.List;

public interface BaseException {

    List<AppsErrorMessage> getAppsErrorMessages();

    int getHttpStatus();

    App_constants.ResponseStatus getResponseStatus();

    Boolean containsErrorCode(String errorCode);
}
