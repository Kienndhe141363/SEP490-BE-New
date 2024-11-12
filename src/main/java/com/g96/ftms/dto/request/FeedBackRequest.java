package com.g96.ftms.dto.request;

import com.g96.ftms.dto.common.PagingBaseParams;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FeedBackRequest {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FeedBackPagingRequest extends PagingBaseParams {
        private String keyword;
        private Boolean status;
        private Long userId;
        private Long classId;
        private Long subjectId;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FeedBackDetailFormRequest {
        private Long feedBackId;
    }
}
