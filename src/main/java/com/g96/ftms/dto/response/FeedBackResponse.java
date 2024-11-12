package com.g96.ftms.dto.response;

import com.g96.ftms.entity.Questions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class FeedBackResponse {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class FeedBackInfoDTO {
        private Long feedbackId;
        private Long userId;
        private String traineeName;
        private String subjectCode;
        private Double avgRating;
        private LocalDateTime openDate;
        private LocalDateTime lastUpdate;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class FeedBackFormDTO {
        private Long feedbackId;
        private String traineeName;
        private String trainerName;
        private Long traineeId;
        private Long trainerId;
        private String subjectCode;
        private String subjectName;
        private Double avgRating;
        private String description;
        private LocalDateTime openDate;
        private LocalDateTime endDate;
        List<QuestionAnswerFormInfoDTO> questionAnswerForm;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class QuestionAnswerFormInfoDTO{
        Questions questions;
        String answer;
    }
}
