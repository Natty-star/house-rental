
package edu.miu.cs590.notification.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest {


    private String gustUserEmail;
    private String hostUserEmail;
    private String propertyName;
    private String propertyTitle;
    private String startDate;
    private String endDate;


}
