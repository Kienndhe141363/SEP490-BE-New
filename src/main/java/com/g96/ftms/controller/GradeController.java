package com.g96.ftms.controller;

import com.g96.ftms.dto.common.PagingBaseParams;
import com.g96.ftms.dto.request.GradeRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.service.grade.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/grade-management")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;
    @PostMapping("/search")
    public ApiResponse<?> getGradeList(@RequestBody GradeRequest.GradePagingRequest model) {
        return gradeService.search(model);
    }

    @PostMapping("/my-grade")
    public ApiResponse<?> getGradeByUser(@RequestBody GradeRequest.GradedDetailRequest model) {
        return gradeService.getGradeDetail(model);
    }

    @PostMapping("/save-grade-setting")
    public ApiResponse<?> saveGradeSetting(@RequestBody GradeRequest.GradeSettingUpdateRequest model) {
        return gradeService.saveGradeSetting(model);
    }
}