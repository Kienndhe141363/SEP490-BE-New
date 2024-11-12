package com.g96.ftms.dto.request;

import com.g96.ftms.dto.common.PagingBaseParams;
import com.g96.ftms.util.constants.SettingGroupEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SettingRequest {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SettingPagingRequest extends PagingBaseParams {
        private String keyword;
        private Boolean status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SettingAddRequest {
        private String settingName;
        @NotNull
        private SettingGroupEnum settingGroup;
        private String description;
        private Boolean status;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SettingEditRequest extends SettingAddRequest{
        private Long id;
    }
}
