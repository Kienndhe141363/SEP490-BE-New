package com.g96.ftms.service.curriculum;

import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.request.CurriculumRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.CurriculumnResponse;
import com.g96.ftms.entity.Curriculum;

public interface  CurriculumService {
    ApiResponse<PagedResponse<Curriculum>> search(CurriculumRequest.CurriculumPagingRequest model);
    ApiResponse<CurriculumnResponse.CurriculumInfoDTO> getCurriculumDetail(Long curriculumId);
    ApiResponse<Curriculum> createCurriculum(CurriculumRequest.CurriculumAddRequest model);
    ApiResponse<Curriculum> updateCurriculum(CurriculumRequest.CurriculumEditRequest model);


}
