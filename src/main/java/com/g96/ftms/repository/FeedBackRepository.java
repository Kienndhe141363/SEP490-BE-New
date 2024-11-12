package com.g96.ftms.repository;

import com.g96.ftms.entity.FeedBack;
import com.g96.ftms.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository  extends JpaRepository<FeedBack,Long> {
    @Query("SELECT f FROM FeedBack f " +
            " WHERE " +
            "( :keywordFilter IS NULL OR (f.user.fullName LIKE :keywordFilter OR f.subject.subjectName LIKE :keywordFilter )) " +
            "AND f.subject.subjectId= :subjectId " +
            "AND f.user.userId= :userId " +
            "AND f.classs.classId= :classId")
    Page<FeedBack> searchFilter(@Param("keywordFilter") String keywordFilter,
                                @Param("userId") Long userId ,@Param("subjectId") Long subjectId,@Param("classId") Long classId,
                                Pageable pageable);
}