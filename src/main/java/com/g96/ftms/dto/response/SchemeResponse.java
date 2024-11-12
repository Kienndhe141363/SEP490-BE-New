package com.g96.ftms.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

public class SchemeResponse {
    @Data
    @NoArgsConstructor
    public static class SubjectSchemeInfo {
        private Integer markSchemeId;
        private String markName;
        private Double markWeight;
    }
}