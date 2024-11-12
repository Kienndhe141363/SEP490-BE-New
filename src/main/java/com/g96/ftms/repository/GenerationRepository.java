package com.g96.ftms.repository;

import com.g96.ftms.entity.Curriculum;
import com.g96.ftms.entity.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenerationRepository  extends JpaRepository<Generation, Long> {
    boolean existsByGenerationName(String generationName);

    Optional<Generation> findByGenerationName(String generationName);
}
