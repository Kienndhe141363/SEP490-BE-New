package com.g96.ftms.repository;

import com.g96.ftms.entity.UserClassRelation;
import com.g96.ftms.entity.UserClassRelationId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClassRelationRepository extends JpaRepository<UserClassRelation, UserClassRelationId> {

    @Query("select distinct u from UserClassRelation u where u.classs.classId = ?1")
    List<UserClassRelation> findDistinctByClasss_ClassId(Long classId, Pageable pageable);
}
