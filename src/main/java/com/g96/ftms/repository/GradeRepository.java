package com.g96.ftms.repository;

import com.g96.ftms.entity.Grade;
import com.g96.ftms.entity.GradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, GradeId> {
    List<Grade> findByUser_UserId(Long userId);

    List<Grade> findByUser_UserIdAndClasss_ClassId(Long userId, Long classId);
}