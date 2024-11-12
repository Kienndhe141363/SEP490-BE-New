package com.g96.ftms.service.classes;

import com.g96.ftms.dto.request.ClassRequest.ClassAddRequest;
import com.g96.ftms.dto.request.ClassRequest.ClassEditRequest;
import com.g96.ftms.dto.request.ClassRequest.ClassPagingRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.response.ClassReponse;

import java.util.List;


public interface ClassService {
    ApiResponse<PagedResponse<ClassReponse.ClassInforDTO>> search(ClassPagingRequest model);

    ApiResponse<?> addClass(ClassAddRequest model);

    ApiResponse<?> updateClass(ClassEditRequest model);

    ApiResponse<ClassReponse.ClassInforDTO> getClassDetail(Long classId);

    ApiResponse<ClassReponse.ClassInforDTO> getClassForTrainee(Long classId);

}
