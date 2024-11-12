package com.g96.ftms.repository;

import com.g96.ftms.entity.MarkScheme;
import com.g96.ftms.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemeRepository  extends JpaRepository<MarkScheme, Long> {
    long deleteBySubject_SubjectId(Long subjectId);
}