package com.g96.ftms.service.setting;

import com.g96.ftms.dto.common.PagedResponse;

import com.g96.ftms.dto.request.SettingRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.SettingResponse;
import com.g96.ftms.entity.Settings;


public interface SettingService {
    ApiResponse<PagedResponse<SettingResponse.SettingInfoDTO>> search(SettingRequest.SettingPagingRequest model);
    ApiResponse<Settings> createSetting(SettingRequest.SettingAddRequest model);
    ApiResponse<Settings> updateSetting(SettingRequest.SettingEditRequest model);
    ApiResponse<SettingResponse.SettingInfoDTO> getDetail(Long id);
}