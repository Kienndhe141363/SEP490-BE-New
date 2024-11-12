package com.g96.ftms.controller;

import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.entity.Subject;
import com.g96.ftms.dto.request.SubjectRequest.SubjectAddRequest;
import com.g96.ftms.dto.request.SubjectRequest.SubjectEditRequest;
import com.g96.ftms.dto.request.SubjectRequest.SubjectPagingRequest;
import com.g96.ftms.service.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    //    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN','ROLE_COORDINATOR','CLASS_ADMIN')")
    @PostMapping("/search")
    public ApiResponse<?> getSubjectList(@RequestBody SubjectPagingRequest model) {
        return subjectService.search(model);
    }

    @GetMapping("/subject-options")
    public ApiResponse<?> getAllSubjectOptions() {
        return subjectService.getAllSubjectOption();
    }


    @GetMapping("/detail/{id}")
    public ApiResponse getSubjectDetail(@PathVariable("id") Long subjectId) {
        return subjectService.getSubjectDetail(subjectId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ApiResponse<Subject> addSubject(@RequestBody SubjectAddRequest model) {
        return subjectService.addSubject(model);
    }

    @PutMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ApiResponse<Subject> updateSubject(@RequestBody SubjectEditRequest model) {
        return subjectService.updateSubject(model);
    }
}
