package com.Inova.ClickupDashboard.exception.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:/errormsg.properties")
public class ExceptionMessageFinder {

    @Autowired
    private Environment env;


    public List<AppsErrorMessage> getMessages(List<AppsErrorMessage> appsErrorMessages) {
        List<AppsErrorMessage> result = new ArrayList<>();
        appsErrorMessages.forEach((appsErrorMessage) -> {
            if (StringUtils.isEmpty(appsErrorMessage.getErrorMessage())) {
                AppsErrorMessage errorMessage = new AppsErrorMessage(appsErrorMessage.getErrorCode(),
                        this.getMessage(appsErrorMessage.getErrorCode()));
                result.add(errorMessage);
            } else {
                result.add(appsErrorMessage);
            }
        });
        return result;
    }

    public String getMessage(String errorCode) {
        String message;
        if (!StringUtils.isEmpty(errorCode)) {
            message = env.getProperty(errorCode);
            if (message == null) {
                message = this.getDefaultMessage();
            }
        } else {
            message = this.getDefaultMessage();
        }

        return message;
    }

    private String getDefaultMessage() {
        return env.getProperty(AppsCommonErrorCode.APPS_DEFAULT_ERROR);
    }
}
