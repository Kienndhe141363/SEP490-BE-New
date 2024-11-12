package com.g96.ftms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectDTO {
    private Long subjectId;
    private String subjectCode;
    private String subjectName;
    private String documentLink;
    private String descriptions;
    private boolean status;
    private double weightPercentage;
    private String createdDate;
    private List<CurriculumDTO> curriculums;
}