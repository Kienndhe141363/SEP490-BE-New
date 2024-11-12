package com.g96.ftms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.io.Serializable;
@Data
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CurriculumSubjectRelationId implements Serializable {
    @Column(name = "curriculum_id")
    private Long curriculumId;

    @Column(name = "subject_id")
    private Long subjectId;
}