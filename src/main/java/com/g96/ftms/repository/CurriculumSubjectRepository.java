package com.g96.ftms.repository;

import com.g96.ftms.entity.Curriculum;
import com.g96.ftms.entity.CurriculumSubjectRelation;
import com.g96.ftms.entity.CurriculumSubjectRelationId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumSubjectRepository extends JpaRepository<CurriculumSubjectRelation, CurriculumSubjectRelationId> {
    @Modifying
    @Query("DELETE FROM CurriculumSubjectRelation c WHERE c.curriculum.curriculumId = :curriculumId")
    void removeRelationByCurriculum(@Param("curriculumId") Long curriculumId);
}
