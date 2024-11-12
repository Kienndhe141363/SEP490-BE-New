package com.g96.ftms.repository;

import com.g96.ftms.entity.GradeSetting;
import com.g96.ftms.entity.MarkScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkSchemeRepository extends JpaRepository<MarkScheme, Long> {
}
