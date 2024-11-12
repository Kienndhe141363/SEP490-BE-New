package com.g96.ftms.dto.request;

import com.g96.ftms.dto.common.PagingBaseParams;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

public class GradeRequest {
    @Data
    @NoArgsConstructor
    public static class GradePagingRequest extends PagingBaseParams {
        private String keyword;
        private Boolean status;
        private Long classId;
    }
    @Data
    @NoArgsConstructor
    public static class GradedDetailRequest {
        private Long userId;
        private Long classId;
    }
    @Data
    @NoArgsConstructor
    public static class GradeSettingUpdateRequest {
        private Long subjectId;
        private Long classId;
        private LocalDateTime dateLock;
        private Map<String,Double >components;
    }
}
