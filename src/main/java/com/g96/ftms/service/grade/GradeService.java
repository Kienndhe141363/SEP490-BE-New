package com.g96.ftms.service.grade;

import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.request.GradeRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.GradeResponse;

import java.util.List;

public interface GradeService {
    ApiResponse<PagedResponse<GradeResponse.GradeInfoDTO>> search(GradeRequest.GradePagingRequest model);

    ApiResponse<GradeResponse.GradeInfoDTO> getGradeDetail(GradeRequest.GradedDetailRequest model);
    ApiResponse<?> saveGradeSetting(GradeRequest.GradeSettingUpdateRequest model);
}
