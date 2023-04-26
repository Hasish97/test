package com.Inova.ClickupDashboard.controllers;


import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class App_constants {

    public enum ResponseStatus {
        SUCCESS, FAILED;
        public static ResponseStatus resolveStatus(String statusStr) {
            ResponseStatus matchingStatus = null;
            if (!StringUtils.isEmpty(statusStr)) {
                matchingStatus = ResponseStatus.valueOf(statusStr.trim());
            }
            return matchingStatus;
        }
    }
    public static final long THREAD_TIME=8000;

    public enum AuthorizationError {
        USER_UNAUTHORIZED("User is unauthorized for action");

        private String description;

        AuthorizationError(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum YesNo {

        Y("Yes", true, 1), N("No", false, 0);

        private String strVal;

        private Boolean boolVal;

        private Integer intVal;

        YesNo(String strVal, Boolean boolVal, Integer intVal) {
            this.strVal = strVal;
            this.boolVal = boolVal;
            this.intVal = intVal;
        }

        public static YesNo valueOf(Boolean boolVal) {
            YesNo matchingStatus = null;
            for (YesNo yesno : YesNo.values()) {
                if (yesno.getBoolVal().equals(boolVal)) {
                    matchingStatus = yesno;
                    break;
                }
            }
            return matchingStatus;
        }

        public static Map<String, String> getYesNoMap() {
            Map<String, String> map = new LinkedHashMap<String, String>();
            for (YesNo yesNo : YesNo.values()) {
                map.put(yesNo.toString(), yesNo.getStrVal());
            }
            return map;
        }

        public String getStrVal() {
            return strVal;
        }

        public Boolean getBoolVal() {
            return boolVal;
        }

        public Integer getIntVal() {
            return intVal;
        }
    }
}
