package com.g96.ftms.service.attendance;

import com.g96.ftms.dto.AttendanceDTO;
import com.g96.ftms.dto.request.AttendanceRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.entity.Attendance;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.repository.AttendanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<List<AttendanceDTO>> getAttendanceListByScheduleId(Long scheduleId) {
        List<Attendance> attendances = attendanceRepository.findByScheduleId(scheduleId);
        List<AttendanceDTO> attendanceDTOs = attendances.stream()
                .map(attendance -> modelMapper.map(attendance, AttendanceDTO.class))
                .collect(Collectors.toList());
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), attendanceDTOs);
    }

    @Override
    public ApiResponse<List<AttendanceDTO>> getAttendanceListByScheduleId(Long scheduleId, Long userId, String role) {
        List<AttendanceDTO> attendanceDTOs;

        if ("COORDINATOR".equalsIgnoreCase(role)) {
            // Coordinator can view the entire attendance list
            List<Attendance> attendances = attendanceRepository.findByScheduleId(scheduleId);
            attendanceDTOs = attendances.stream()
                    .map(attendance -> modelMapper.map(attendance, AttendanceDTO.class))
                    .collect(Collectors.toList());
        } else if ("TRAINEE".equalsIgnoreCase(role)) {
            // Trainee can only view their own attendance list
            List<Attendance> attendances = attendanceRepository.findByScheduleIdAndUserId(scheduleId, userId);
            attendanceDTOs = attendances.stream()
                    .map(attendance -> modelMapper.map(attendance, AttendanceDTO.class))
                    .collect(Collectors.toList());
        } else {
            // If the role is not valid, return an empty list or an error message
            attendanceDTOs = List.of(); // or throw new CustomException("Unauthorized access");
        }

        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), attendanceDTOs);
    }
    @Override
    public ApiResponse<?> submitAttendance(List<AttendanceRequest> attendanceRequests) {
        List<Attendance> attendances = attendanceRequests.stream()
                .map(request -> modelMapper.map(request, Attendance.class))
                .collect(Collectors.toList());

        attendanceRepository.saveAll(attendances);
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), attendances);
    }
}
