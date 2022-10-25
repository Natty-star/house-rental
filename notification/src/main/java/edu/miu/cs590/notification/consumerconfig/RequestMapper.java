package edu.miu.cs590.notification.consumerconfig;




import edu.miu.cs590.notification.dao.LogObject;
import edu.miu.cs590.notification.dao.NotificationRequest;

import java.util.List;

public class RequestMapper {

    // Map json object to NotificationRequest and LogObject and return them
    public NotificationRequest mapRequest(List<NotificationRequest> notificationRequests) {


        String gustUserEmail =String.valueOf(notificationRequests.get(0))
                .substring(18,String.valueOf(notificationRequests.get(0)).length()-1);

        String hostUserEmail =String.valueOf(notificationRequests.get(1))
                .substring(17,String.valueOf(notificationRequests.get(1)).length()-1);

        String propertyName =String.valueOf(notificationRequests.get(2))
                .substring(16,String.valueOf(notificationRequests.get(2)).length()-1);

        String propertyTitle =String.valueOf(notificationRequests.get(3))
                .substring(17,String.valueOf(notificationRequests.get(3)).length()-1);


        String startDate =String.valueOf(notificationRequests.get(4))
                .substring(13,String.valueOf(notificationRequests.get(4)).length()-1);

        String endDate =String.valueOf(notificationRequests.get(5))
                .substring(11,String.valueOf(notificationRequests.get(5)).length()-2);


        NotificationRequest mappedRequest = NotificationRequest.builder()
                .gustUserEmail(gustUserEmail)
                .hostUserEmail(hostUserEmail)
                .propertyName(propertyName)
                .propertyTitle(propertyTitle)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return mappedRequest;
    }

    public LogObject logObjectMapper(List<LogObject> logObjects) {

        String status = String.valueOf(logObjects.get(0))
                .substring(11, String.valueOf(logObjects.get(0)).length() - 1);

        String email = String.valueOf(logObjects.get(1))
                .substring(9, String.valueOf(logObjects.get(1)).length() - 1);

        String reservationId = String.valueOf(logObjects.get(2))
                .substring(17, String.valueOf(logObjects.get(2)).length() - 1);

        String paymentType = String.valueOf(logObjects.get(3))
                .substring(15, String.valueOf(logObjects.get(3)).length() - 2);

        return LogObject.builder()
                .status(status)
                .email(email)
                .reservationId(reservationId)
                .paymentType(paymentType)
                .build();
    }


}
