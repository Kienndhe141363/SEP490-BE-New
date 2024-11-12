package com.g96.ftms.repository;

import com.g96.ftms.entity.Attendance;
import com.g96.ftms.entity.AttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {
    List<Attendance> findByScheduleId(Long scheduleId);
    List<Attendance> findByScheduleIdAndUserId(Long scheduleId, Long userId);
}
