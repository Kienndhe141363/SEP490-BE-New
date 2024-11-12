package com.g96.ftms.dto.response;

import com.g96.ftms.dto.common.PagingBaseParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SettingResponse {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SettingInfoDTO {
        private Long id;
        private String settingName;
        private String settingGroup;
        private String description;
        private Boolean status;
    }
}
