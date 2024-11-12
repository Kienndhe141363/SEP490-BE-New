package com.g96.ftms.repository;

import com.g96.ftms.entity.Curriculum;
import com.g96.ftms.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    boolean existsByCurriculumName(String curriculumName);

    @Query("SELECT c FROM Curriculum c WHERE " +
            "(:keywordFilter IS NULL OR (c.curriculumName LIKE :keywordFilter OR c.descriptions LIKE :keywordFilter)) " +
            "AND (:status IS NULL OR c.status= :status) ")
    Page<Curriculum> searchFilter(@Param("keywordFilter") String keywordFilter,
                                  @Param("status") Boolean status,
                                  Pageable pageable);
}
