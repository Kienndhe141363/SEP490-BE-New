package com.g96.ftms.repository;

import com.g96.ftms.entity.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClassRepository extends JpaRepository<Class, Long> {
    @Query("SELECT c FROM Class c WHERE " +
            "(:keywordFilter IS NULL OR (c.classCode LIKE :keywordFilter OR c.descriptions LIKE :keywordFilter)) " +
            "AND (:status IS NULL OR c.status= :status) ")
    Page<Class> searchFilter(@Param("keywordFilter") String keywordFilter,
                             @Param("status") Boolean status,
                             Pageable pageable);
    boolean existsByClassCode(String classCode);
}
