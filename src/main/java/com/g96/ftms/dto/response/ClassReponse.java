package com.g96.ftms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ClassReponse {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClassInforDTO {
        private Long classId;
        private String classCode;
        private String descriptions;
        private Boolean status;
        private String admin;
        private LocalDateTime endDate ;
        private LocalDateTime startDate;
        private LocalDateTime createdDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GradeTableInforDTO {
        private String id;
        private String subjectName;
        private String grade;
    }
}