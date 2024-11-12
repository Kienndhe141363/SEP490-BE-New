package com.g96.ftms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumDTO {
    private Long curriculumId;
    private String curriculumName;
    private String descriptions;
    private String createdDate;
    private Boolean status;
    private List<SubjectDTO> subjects;
}
