package com.g96.ftms.controller;


import com.g96.ftms.dto.request.ClassRequest.ClassAddRequest;
import com.g96.ftms.dto.request.ClassRequest.ClassEditRequest;
import com.g96.ftms.dto.request.ClassRequest.ClassPagingRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.service.classes.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/class-management")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping("/search")
    public ApiResponse<?> getClassList(@RequestBody ClassPagingRequest model) {
        return classService.search(model);
    }
    @PostMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR')")
    public ApiResponse<?> addClass(@RequestBody ClassAddRequest model){
        return classService.addClass(model);
    }
    @GetMapping("/detail/{id}")
    public  ApiResponse getClassDetail(@PathVariable("id")  Long classId){
        return classService.getClassDetail(classId);
    }
    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR')")
    public ApiResponse<?> updateClass(@RequestBody ClassEditRequest model){
        return classService.updateClass(model);
    }

    @PostMapping("/info-trainee/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COORDINATOR','ROLE_CLASS_ADMIN')")
    public ApiResponse<?> getInfoForTrainee(@PathVariable("id") Long classId) {
        return classService.getClassForTrainee(classId);
    }
}