package com.g96.ftms.repository;

import com.g96.ftms.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s WHERE " +
            "(:keywordFilter IS NULL OR (s.subjectName LIKE :keywordFilter OR s.descriptions LIKE :keywordFilter OR s.subjectCode LIKE :keywordFilter)) " +
            "AND (:status IS NULL OR s.status= :status) ")
    Page<Subject> searchFilter(@Param("keywordFilter") String keywordFilter,
                               @Param("status") Boolean status,
                               Pageable pageable);

    boolean existsBySubjectName(String subjectName);

    boolean existsBySubjectCode(String subjectCode);
    Subject findBySubjectCode(String subjectCode);
    List<Subject> findByStatusTrue();
    List<Subject> findDistinctByCurriculumSubjectRelationList_Curriculum_CurriculumId(Long curriculumId);
}