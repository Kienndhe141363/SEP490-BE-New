package com.g96.ftms.entity;

import java.io.Serializable;
import java.util.Objects;

public class AttendanceId implements Serializable {
    private Long scheduleId;
    private Long userId;

    // Constructors, equals, hashCode
    public AttendanceId() {}

    public AttendanceId(Long scheduleId, Long userId) {
        this.scheduleId = scheduleId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceId that = (AttendanceId) o;
        return Objects.equals(scheduleId, that.scheduleId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, userId);
    }
}
