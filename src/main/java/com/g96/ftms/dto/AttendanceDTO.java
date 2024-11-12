package com.g96.ftms.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AttendanceDTO {
    private Long scheduleId;
    private Long userId;
    private Boolean status;
    private LocalDateTime recordTime;
    private String attendanceNote;
}
