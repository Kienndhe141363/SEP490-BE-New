package com.g96.ftms.controller;

import com.g96.ftms.dto.AttendanceDTO;
import com.g96.ftms.dto.request.AttendanceRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.service.attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/list/{scheduleId}")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getAttendanceListByScheduleId(
            @PathVariable Long scheduleId,
            @RequestParam Long userId,  // ID of the current user
            @RequestParam String role) { // Role of the user (COORDINATOR or TRAINEE)

        ApiResponse<List<AttendanceDTO>> response = attendanceService.getAttendanceListByScheduleId(scheduleId, userId, role);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_CLASS_ADMIN','ROLE_TRAINER')")
    public ApiResponse<?> submitAttendance(@RequestBody List<AttendanceRequest> attendanceRequests) {
        return attendanceService.submitAttendance(attendanceRequests);
    }
}
