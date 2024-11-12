package com.g96.ftms.service.subject;

import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.request.SubjectRequest.SubjectAddRequest;
import com.g96.ftms.dto.request.SubjectRequest.SubjectEditRequest;
import com.g96.ftms.dto.request.SubjectRequest.SubjectPagingRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.SubjectResponse;
import com.g96.ftms.entity.Subject;

import java.util.List;

public interface SubjectService {
    ApiResponse<PagedResponse<Subject>> search(SubjectPagingRequest model);

    ApiResponse<SubjectResponse.SubjectInfoDTO> getSubjectDetail(Long subjectId);

    ApiResponse<Subject> updateSubject(SubjectEditRequest model);

    ApiResponse<Subject> addSubject(SubjectAddRequest model);

    ApiResponse<List<SubjectResponse.SubjectOptionDTO>> getAllSubjectOption();

}
