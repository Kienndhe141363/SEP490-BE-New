package com.g96.ftms.service.subject;

import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.request.SubjectRequest.SubjectAddRequest;
import com.g96.ftms.dto.request.SubjectRequest.SubjectEditRequest;
import com.g96.ftms.dto.request.SubjectRequest.SubjectPagingRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.SchemeResponse;
import com.g96.ftms.dto.response.SubjectResponse;
import com.g96.ftms.entity.MarkScheme;
import com.g96.ftms.entity.Subject;
import com.g96.ftms.exception.AppException;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.repository.CurriculumRepository;
import com.g96.ftms.repository.SchemeRepository;
import com.g96.ftms.repository.SubjectRepository;
import com.g96.ftms.util.SqlBuilderUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final   CurriculumRepository curriculumRepository;
    private final SchemeRepository schemeRepository;
    private final ModelMapper mapper;

    @Override
    public ApiResponse<PagedResponse<Subject>> search(SubjectPagingRequest model) {
        String keywordFilter = SqlBuilderUtils.createKeywordFilter(model.getKeyword());
        Page<Subject> pages = subjectRepository.searchFilter(keywordFilter, model.getStatus(), model.getPageable());
        PagedResponse<Subject> response = new PagedResponse<>(pages.getContent(), pages.getNumber(), pages.getSize(), pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }

    @Override
    public ApiResponse<SubjectResponse.SubjectInfoDTO> getSubjectDetail(Long subjectId) {

        if (subjectId == null || subjectId <= 0) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT);
        }

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() ->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.SUBJECT_NOT_FOUND));

        SubjectResponse.SubjectInfoDTO map = mapper.map(subject, SubjectResponse.SubjectInfoDTO.class);
        List<SchemeResponse.SubjectSchemeInfo> schemeList = mapper.map(subject.getMarkSchemeList(), new TypeToken<List<SchemeResponse.SubjectSchemeInfo>>() {
        }.getType());
        map.setSchemes(schemeList);
        return new ApiResponse<SubjectResponse.SubjectInfoDTO>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), map);
    }

    @Override
    @Transactional
    public ApiResponse<Subject> updateSubject(SubjectEditRequest model) {
        // Check if the subject exists with the given ID
        Subject subject = subjectRepository.findById(model.getId()).orElseThrow(() ->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.SUBJECT_NOT_FOUND));

        // Check if the subject code is unique
        if (!Objects.equals(subject.getSubjectCode(), model.getSubjectCode())
                && subjectRepository.existsBySubjectCode(model.getSubjectCode())) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_SUBJECT_CODE);
        }

        // Map the incoming request details to the existing subject
        mapper.map(model, subject);

        // Update related schemes
        schemeRepository.deleteBySubject_SubjectId(subject.getSubjectId()); // Delete old schemes

        // Map and save the new schemes
        List<MarkScheme> schemeList = model.getSchemes().stream().map(s -> {
            MarkScheme scheme = mapper.map(s, MarkScheme.class);
            scheme.setStatus(true);
            scheme.setSubject(subject);
            return scheme;
        }).toList();
        List<MarkScheme> schemeListSaved = schemeRepository.saveAll(schemeList);

        subject.setMarkSchemeList(schemeListSaved); // Set updated schemes
        subjectRepository.save(subject); // Save the updated subject

        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), subject);
    }

    @Override
    @Transactional
    public ApiResponse<Subject> addSubject(SubjectAddRequest model) {
        //check exist code
        if (subjectRepository.existsBySubjectCode(model.getSubjectCode())) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_SUBJECT_CODE);
        }
        //save sheme
        Subject map = mapper.map(model, Subject.class);
        Subject subject = subjectRepository.save(map);
        List<MarkScheme> schemeList = model.getSchemes().stream().map(s -> {
            MarkScheme scheme = mapper.map(s, MarkScheme.class);
            scheme.setStatus(true);
            scheme.setSubject(subject);
            return scheme;
        }).collect(Collectors.toList());
        //create scheme
        schemeRepository.saveAll(schemeList);
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), map);
    }

    @Override
    public ApiResponse<List<SubjectResponse.SubjectOptionDTO>> getAllSubjectOption() {
        List<Subject> list = subjectRepository.findByStatusTrue();
        List<SubjectResponse.SubjectOptionDTO> response = mapper.map(list, new TypeToken<List<SubjectResponse.SubjectOptionDTO>>() {
        }.getType());
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }

}
