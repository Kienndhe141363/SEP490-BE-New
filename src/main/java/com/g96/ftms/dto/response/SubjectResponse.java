package com.g96.ftms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class SubjectResponse {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SubjectInfoDTO {
        private Long subjectId;
        private String subjectCode;
        private String subjectName;
        private String documentLink;
        private String descriptions;
        private boolean status;
        private double weightPercentage;
        private String createdDate;
        private List<SchemeResponse.SubjectSchemeInfo> schemes;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SubjectOptionDTO {
        private Long subjectId;
        private String subjectCode;
        private String subjectName;
        private String documentLink;
        private String descriptions;
        private boolean status;
    }
}