package com.g96.ftms.controller;

import com.g96.ftms.dto.request.SettingRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.service.setting.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings")
public class SettingController {

    private final SettingService settingService;

    @PostMapping("/search")
    public ApiResponse<?> search(@RequestBody SettingRequest.SettingPagingRequest model) {
        return settingService.search(model);
    }

    @PostMapping("/create")
    public ApiResponse<?> createSetting(@RequestBody SettingRequest.SettingAddRequest model) {
        return settingService.createSetting(model);
    }
    @PutMapping("/save")
    public ApiResponse<?> updateSetting(@RequestBody SettingRequest.SettingEditRequest model) {
        return settingService.updateSetting(model);
    }
    @GetMapping("/detail/{id}")
    public ApiResponse<?> updateSetting(@PathVariable("id") Long id) {
        return settingService.getDetail(id);
    }
}