package com.g96.ftms.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeId implements Serializable {

    private Long userId;
    private Long classId;
    private Long subjectId;
    private Long markSchemeId;
}