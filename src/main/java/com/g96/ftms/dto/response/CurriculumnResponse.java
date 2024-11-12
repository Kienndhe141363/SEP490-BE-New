package com.g96.ftms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CurriculumnResponse {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurriculumInfoDTO {
        private Long curriculumId;
        private String curriculumName;
        private String descriptions;
        private String createdDate;
        private Boolean status;
        private List<SubjectResponse.SubjectInfoDTO> subjects;
    }
}
