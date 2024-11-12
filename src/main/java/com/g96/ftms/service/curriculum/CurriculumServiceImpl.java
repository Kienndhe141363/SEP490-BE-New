package com.g96.ftms.service.curriculum;

import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.request.CurriculumRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.CurriculumnResponse;
import com.g96.ftms.dto.response.SubjectResponse;
import com.g96.ftms.entity.Curriculum;
import com.g96.ftms.entity.CurriculumSubjectRelation;
import com.g96.ftms.entity.CurriculumSubjectRelationId;
import com.g96.ftms.entity.Subject;
import com.g96.ftms.exception.AppException;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.repository.CurriculumRepository;
import com.g96.ftms.repository.CurriculumSubjectRepository;
import com.g96.ftms.repository.SubjectRepository;
import com.g96.ftms.util.SqlBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {
    private final CurriculumRepository curriculumRepository;
    private final ModelMapper mapper;
    private final SubjectRepository subjectRepository;
    private final CurriculumSubjectRepository curriculumSubjectRepository;

    @Override
    public ApiResponse<PagedResponse<Curriculum>> search(CurriculumRequest.CurriculumPagingRequest model) {
        String keywordFilter = SqlBuilderUtils.createKeywordFilter(model.getKeyword());
        Page<Curriculum> pages = curriculumRepository.searchFilter(keywordFilter, model.getStatus(), model.getPageable());
        PagedResponse<Curriculum> response = new PagedResponse<>(pages.getContent(), pages.getNumber(), pages.getSize(), pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }
    @Override
    @Transactional //roll back if failed
    public ApiResponse<Curriculum> createCurriculum(CurriculumRequest.CurriculumAddRequest model) {
        // check curriculum name exist
        if (curriculumRepository.existsByCurriculumName(model.getCurriculumName())) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_CURRICULUM_NAME);
        }
        //save entity
        Curriculum curriculum = mapper.map(model, Curriculum.class);
        curriculumRepository.save(curriculum);

        //save relation
        addBatchSubjectCurriculumRelation(curriculum, model.getSubjectList());
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), curriculum);
    }
    @Override
    @Transactional
    public ApiResponse<Curriculum> updateCurriculum(CurriculumRequest.CurriculumEditRequest model) {
        // Kiểm tra nếu tồn tại curriculum với ID đã cho
        Curriculum curriculum = curriculumRepository.findById(model.getId()).orElseThrow(() ->
                new AppException(HttpStatus.BAD_REQUEST, ErrorCode.CURRICULUM_NOT_FOUND));

        // Kiểm tra tính duy nhất của curriculum name
        if (!curriculum.getCurriculumName().equalsIgnoreCase(model.getCurriculumName())
                && curriculumRepository.existsByCurriculumName(model.getCurriculumName())) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_CURRICULUM_NAME);
        }

        // Cập nhật các thuộc tính của curriculum từ model
        mapper.map(model, curriculum);

        // Xóa tất cả các mối quan hệ với subject cũ
        curriculumSubjectRepository.removeRelationByCurriculum(curriculum.getCurriculumId());

        addBatchSubjectCurriculumRelation(curriculum, model.getSubjectList());
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(),curriculum);
    }
    @Override
    public ApiResponse<CurriculumnResponse.CurriculumInfoDTO> getCurriculumDetail(Long id) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, ErrorCode.CURRICULUM_NOT_FOUND));
        // Map each subject in CurriculumSubjectRelation to SubjectDTO
        List<SubjectResponse.SubjectInfoDTO> subjectDTOs = curriculum.getCurriculumSubjectRelationList().stream()
                .map(relation -> {
                    Subject subject = relation.getSubject();
                    // Map Subject entity to SubjectDTO
                    return SubjectResponse.SubjectInfoDTO.builder()
                            .subjectId(subject.getSubjectId())
                            .subjectCode(subject.getSubjectCode())
                            .subjectName(subject.getSubjectName())
                            .documentLink(subject.getDocumentLink())
                            .descriptions(subject.getDescriptions())
                            .status(subject.isStatus())
                            .weightPercentage(relation.getWeightPercentage())
                            .createdDate(subject.getCreatedDate().toString())
                            // Skip mapping curriculums to avoid circular reference
                            .build();
                }).toList();

        // Build response
        CurriculumnResponse.CurriculumInfoDTO response = CurriculumnResponse.CurriculumInfoDTO.builder()
                .curriculumId(curriculum.getCurriculumId())
                .curriculumName(curriculum.getCurriculumName())
                .descriptions(curriculum.getDescriptions())
                .createdDate(curriculum.getCreatedDate().toString())
                .status(curriculum.getStatus())
                .subjects(subjectDTOs) // Add subject DTOs
                .build();
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }


    public void addBatchSubjectCurriculumRelation(Curriculum curriculum, List<CurriculumRequest.CurriculumSubjectAdd> subjectList) {
        List<CurriculumSubjectRelation> subjectRelations = new ArrayList<>();
        for (CurriculumRequest.CurriculumSubjectAdd s : subjectList) {
            // Tìm subject theo subject code
            Subject subject = subjectRepository.findBySubjectCode(s.getCode());
            if (subject == null) {
                throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.SUBJECT_NOT_FOUND);
            }

            // Tạo id cho quan hệ curriculum - subject
            CurriculumSubjectRelationId id = CurriculumSubjectRelationId.builder()
                    .curriculumId(curriculum.getCurriculumId())
                    .subjectId(subject.getSubjectId())
                    .build();

            // Tạo và thêm quan hệ curriculum - subject vào danh sách
            CurriculumSubjectRelation relation = CurriculumSubjectRelation.builder()
                    .subject(subject)
                    .curriculum(curriculum)
                    .weightPercentage(s.getPercentage())
                    .id(id)
                    .build();
            subjectRelations.add(relation);
        }

        // Lưu tất cả các quan hệ cùng lúc
        curriculumSubjectRepository.saveAll(subjectRelations);
    }

}
