package com.g96.ftms.dto.request;

import com.g96.ftms.dto.common.PagingBaseParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CurriculumRequest {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CurriculumPagingRequest extends PagingBaseParams {
        private String keyword;
        private Boolean status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurriculumAddRequest {
        private String curriculumName;
        private String descriptions;
        private Boolean status = true;
        List<CurriculumSubjectAdd>subjectList;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurriculumEditRequest extends CurriculumAddRequest {
        private Long id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurriculumSubjectAdd {
        private String code;
        private Double percentage;
    }
}
