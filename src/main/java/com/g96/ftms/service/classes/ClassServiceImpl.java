package com.g96.ftms.service.classes;

import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.request.ClassRequest.ClassAddRequest;
import com.g96.ftms.dto.request.ClassRequest.ClassEditRequest;
import com.g96.ftms.dto.request.ClassRequest.ClassPagingRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.ClassReponse;
import com.g96.ftms.entity.Subject;
import com.g96.ftms.entity.Room;
import com.g96.ftms.entity.User;
import com.g96.ftms.entity.Schedule;
import com.g96.ftms.entity.Class;
import com.g96.ftms.exception.AppException;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.repository.ClassRepository;
import com.g96.ftms.repository.UserRepository;
import com.g96.ftms.repository.ScheduleRepository;
import com.g96.ftms.repository.SubjectRepository;
import com.g96.ftms.repository.RoomRepository;
import com.g96.ftms.util.SqlBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final SubjectRepository subjectRepository;
    private final ScheduleRepository scheduleRepository;
    private final RoomRepository roomRepository;


    @Override
    public ApiResponse<PagedResponse<ClassReponse.ClassInforDTO>> search(ClassPagingRequest model) {
        String keywordFilter = SqlBuilderUtils.createKeywordFilter(model.getKeyword());
        Page<Class> pages = classRepository.searchFilter(keywordFilter, model.getStatus(), model.getPageable());
        List<ClassReponse.ClassInforDTO> collect = pages.getContent().stream().map(s -> {
            return mapper.map(s, ClassReponse.ClassInforDTO.class);
        }).collect(Collectors.toList());
        PagedResponse<ClassReponse.ClassInforDTO> response = new PagedResponse<>(collect, pages.getNumber(), pages.getSize(), pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }

    @Override
    @Transactional
    public ApiResponse<?> addClass(ClassAddRequest model) {
        // check admin exist
        User user = userRepository.findByAccount(model.getAdmin());
        if (user == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.USER_NOT_FOUND);
        }
        //check room Exist
        Room room = roomRepository.findById(model.getRoomId()).orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, ErrorCode.ROOM_NOT_FOUND));

        Class map = mapper.map(model, Class.class);
        //save entity
        Class classSave = classRepository.save(map);
        //create schedule
        List<Subject> subjectsInCurriculum = subjectRepository.findDistinctByCurriculumSubjectRelationList_Curriculum_CurriculumId(model.getCurriculumId());
        List<Schedule> scheduleList = new ArrayList<>();
        for (Subject subject : subjectsInCurriculum) {
            Schedule schedule = Schedule.builder().startTime(model.getStartDate()).endTime(model.getEndDate()).status(true)
                    .classs(map).subject(subject).room(room).userId(user.getUserId()).description(model.getDescriptions()).build();
            scheduleList.add(schedule);
        }
        //save scheduleList
        scheduleRepository.saveAll(scheduleList);
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), classSave);
    }
    @Override
    @Transactional
    public ApiResponse<Class> updateClass(ClassEditRequest model) {
        // Check if the class exists with the given ID
        Class classToUpdate = classRepository.findById(model.getId()).orElseThrow(() ->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.CLASS_NOT_FOUND));

        // Check if the class code is unique
        if (!Objects.equals(classToUpdate.getClassCode(), model.getClassCode())
                && classRepository.existsByClassCode(model.getClassCode())) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_CLASS_CODE);
        }

        // Map updated fields from the request to the existing class entity
        mapper.map(model, classToUpdate);

        // Check if admin user exists
        User user = userRepository.findByAccount(model.getAdmin());
        if (user == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.USER_NOT_FOUND);
        }

        // Check if the room exists
        Room room = roomRepository.findById(model.getRoomId()).orElseThrow(() ->
                new AppException(HttpStatus.BAD_REQUEST, ErrorCode.ROOM_NOT_FOUND));

        // Delete old schedules for this class
        scheduleRepository.deleteByClasss_ClassId(classToUpdate.getClassId());

        // Add updated schedules based on the curriculum
        List<Subject> subjectsInCurriculum = subjectRepository.findDistinctByCurriculumSubjectRelationList_Curriculum_CurriculumId(model.getCurriculumId());
        List<Schedule> newSchedules = subjectsInCurriculum.stream().map(subject -> {
            return Schedule.builder()
                    .startTime(model.getStartDate())
                    .endTime(model.getEndDate())
                    .status(true)
                    .classs(classToUpdate)
                    .subject(subject)
                    .room(room)
                    .userId(user.getUserId())
                    .description(model.getDescriptions())
                    .build();
        }).collect(Collectors.toList());

        // Save the new schedules and updated class entity
        scheduleRepository.saveAll(newSchedules);
        classRepository.save(classToUpdate);

        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), classToUpdate);
    }



    @Override
    public ApiResponse<ClassReponse.ClassInforDTO> getClassDetail(Long classId){
        if (classId == null || classId <= 0) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT);
        }

        Class c = classRepository.findById(classId).orElseThrow(() ->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.CLASS_NOT_FOUND));
        ClassReponse.ClassInforDTO response = mapper.map(c, ClassReponse.ClassInforDTO.class);
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }

    @Override
    public ApiResponse<ClassReponse.ClassInforDTO> getClassForTrainee(Long classId) {
        if (classId == null || classId <= 0) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT);
        }

        Class classEntity = classRepository.findById(classId).orElseThrow(() ->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.CLASS_NOT_FOUND));
        ClassReponse.ClassInforDTO response = mapper.map(classEntity, ClassReponse.ClassInforDTO.class);
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
    }
}
