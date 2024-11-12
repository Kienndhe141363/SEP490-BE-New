package com.g96.ftms.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AttendanceId.class)  // Khóa chính phức hợp
public class Attendance {

    @Id
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private Boolean status = true;  // Mặc định là có mặt

    @Column(name = "record_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime recordTime = LocalDateTime.now();

    @Column(name = "attendance_note", columnDefinition = "TEXT")
    private String attendanceNote;
}
