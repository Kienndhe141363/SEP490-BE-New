package com.g96.ftms.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequest {
    private Long userId;
    private Long scheduleId;
    private Boolean status;
    private String note;


}
