package com.g96.ftms.service.attendance;

import com.g96.ftms.dto.AttendanceDTO;
import com.g96.ftms.dto.request.AttendanceRequest;
import com.g96.ftms.dto.response.ApiResponse;

import java.util.List;

public interface AttendanceService {
    ApiResponse<List<AttendanceDTO>> getAttendanceListByScheduleId(Long scheduleId);
    ApiResponse<List<AttendanceDTO>> getAttendanceListByScheduleId(Long scheduleId, Long userId, String role);
    ApiResponse<?> submitAttendance(List<AttendanceRequest> attendanceRequests);
}