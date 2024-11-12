package com.g96.ftms.repository;

import com.g96.ftms.entity.GradeSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeSettingRepository extends JpaRepository<GradeSetting, Long> {
    GradeSetting findByClasss_ClassIdAndSubject_SubjectId(Long classId, Long subjectId);
}

