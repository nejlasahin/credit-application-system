package com.project.backend.util.notificationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    String successfulMessage = "Your credit has been approved.";
    String unsuccessfulMessage = "Your credit has not been approved.";

    public Boolean sendSms(String phoneNumber, Boolean status) {
        getMessage(status);
        log.info("Notification: " + getMessage(status));

        /*

         ...

         */

        return true;
    }

    public String getMessage(Boolean status) {
        if(status){
            return successfulMessage;
        }
        return unsuccessfulMessage;
    }
}
