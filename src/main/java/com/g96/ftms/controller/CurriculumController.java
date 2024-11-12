package com.g96.ftms.controller;

import com.g96.ftms.dto.CurriculumDTO;
import com.g96.ftms.dto.request.CurriculumRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.service.curriculum.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/curriculums")
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;

    @PostMapping("/search")
    public ApiResponse search(@RequestBody CurriculumRequest.CurriculumPagingRequest model) {
        return curriculumService.search(model);
    }
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR')")  // Chỉ cho phép admin và coordinator tạo mới
    public ApiResponse<?> createCurriculum(@RequestBody CurriculumRequest.CurriculumAddRequest model) {
        return curriculumService.createCurriculum(model);
    }
    @GetMapping("/detail/{curriculumId}")
    public ApiResponse<?> getCurriculumDetails(@PathVariable Long curriculumId) {
        return curriculumService.getCurriculumDetail(curriculumId);
    }

    // Chỉ system admin và coordinator có thể chỉnh sửa
    @PutMapping("/update/{curriculumId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR')")
    public  ApiResponse<?> updateCurriculum(@RequestBody CurriculumRequest.CurriculumEditRequest model) {
        return curriculumService.updateCurriculum(model);
    }






}
