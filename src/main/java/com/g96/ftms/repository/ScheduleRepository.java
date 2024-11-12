package com.g96.ftms.repository;

import com.g96.ftms.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    void deleteByClasss_ClassId(Long classId);
}