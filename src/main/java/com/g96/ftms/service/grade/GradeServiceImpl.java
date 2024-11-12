package com.g96.ftms.service.grade;

import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.request.GradeRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.GradeResponse;
import com.g96.ftms.entity.*;
import com.g96.ftms.entity.Class;
import com.g96.ftms.exception.AppException;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final UserClassRelationRepository userClassRelationRepository;
    private final GradeRepository gradeRepository;
    private final GradeSettingRepository gradeSettingRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final MarkSchemeRepository markSchemeRepository;

    @Override
    public ApiResponse<PagedResponse<GradeResponse.GradeInfoDTO>> search(GradeRequest.GradePagingRequest model) {
        List<GradeResponse.GradeInfoDTO> list = new ArrayList<>();

        // Tìm danh sách `User` trong class với `Pageable`
        Page<User> pages = userRepository.findUsersByClassId(model.getClassId(), model.getPageable());

        for (User user : pages.getContent()) {
            // Lấy danh sách điểm của `User` theo classId
            GradeResponse.GradeInfoDTO gradesByUserId = getGradesByUserId(user.getUserId(), model.getClassId());
            list.add(gradesByUserId);
        }

        // Tạo `Page<GradeInfoDTO>` từ danh sách `list`
        Page<GradeResponse.GradeInfoDTO> gradeInfoPage = new PageImpl<>(list, model.getPageable(), pages.getTotalElements());

        // Đóng gói `PagedResponse` từ `Page<GradeInfoDTO>`
        PagedResponse<GradeResponse.GradeInfoDTO> response = new PagedResponse<>(
                gradeInfoPage.getContent(),
                gradeInfoPage.getNumber(),
                gradeInfoPage.getSize(),
                gradeInfoPage.getTotalElements(),
                gradeInfoPage.getTotalPages(),
                gradeInfoPage.isLast()
        );

        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }

    public GradeResponse.GradeInfoDTO getGradesByUserId(Long userId,Long classId) {
        // Lấy danh sách điểm của người dùng (userId)
        List<Grade> userGrades = gradeRepository.findByUser_UserIdAndClasss_ClassId(userId,classId);

        if (userGrades.isEmpty()) {
            return null; // Hoặc có thể trả về ngoại lệ tùy theo yêu cầu
        }

        // Lấy thông tin người dùng từ danh sách điểm
        User user = userRepository.findById(userId).orElse(null);

        // Nhóm các bản ghi `Grade` theo `subject` và tính tổng điểm theo trọng số cho từng môn học
        Map<String, Double> subjectTotals = userGrades.stream()
                .collect(Collectors.groupingBy(
                        grade -> grade.getSubject().getSubjectName(),
                        Collectors.summingDouble(grade -> grade.getGrade() * grade.getMarkScheme().getMarkWeight())
                ));

        // Chuyển đổi Map thành danh sách `GradeComponent`, mỗi `GradeComponent` đại diện cho một môn học
        List<GradeResponse.GradeComponent> gradeComponents = subjectTotals.entrySet().stream()
                .map(entry -> new GradeResponse.GradeComponent(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Tính tổng điểm trung bình của người dùng này qua tất cả các môn học
        double total = subjectTotals.values().stream().mapToDouble(Double::doubleValue).sum()/subjectTotals.size();

        // Trả về đối tượng GradeInfoDTO chứa thông tin của người dùng và danh sách điểm theo môn học
        return GradeResponse.GradeInfoDTO.builder()
                .userId(user.getUserId())
                .traineeName(user.getFullName())
                .gradeComponentList(gradeComponents)
                .total(total)
                .build();
    }


    @Override
    public ApiResponse<GradeResponse.GradeInfoDTO> getGradeDetail(GradeRequest.GradedDetailRequest model) {
        Class c = classRepository.findById(model.getClassId()).orElseThrow(() ->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.CLASS_NOT_FOUND));
        List<GradeSetting> gradeSettingList = c.getGradeSettingList();
        //get grade infor
        GradeResponse.GradeInfoDTO gradesByUserId = getGradesByUserId(model.getUserId(), model.getClassId());
        //update grade weight
        List<GradeResponse.GradeComponent> newComponentGrades = gradesByUserId.getGradeComponentList().stream().peek(item->{
            for (GradeSetting gradeSetting:gradeSettingList){
                if(gradeSetting.getSubject().getSubjectName().equalsIgnoreCase(item.getSubjectName())){
                    item.setWeight(gradeSetting.getMarkScheme().getMarkWeight());
                }
            }
        }).toList();

        gradesByUserId.setGradeComponentList(newComponentGrades);

        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), gradesByUserId);
    }

    @Override
    @Transactional
    public ApiResponse<?> saveGradeSetting(GradeRequest.GradeSettingUpdateRequest model) {
        GradeSetting setting = gradeSettingRepository.findByClasss_ClassIdAndSubject_SubjectId(model.getClassId(), model.getSubjectId());

        Class classs = classRepository.findById(model.getClassId()).orElseThrow(() ->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.CLASS_NOT_FOUND));

        Subject subject = subjectRepository.findById(model.getSubjectId()).orElseThrow(() ->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.SUBJECT_NOT_FOUND));
        if(setting == null){ //have not setting yet -->create new
            GradeSetting g= GradeSetting.builder().classs(classs).subject(subject).isLock(false).build();
            GradeSetting settingSave = gradeSettingRepository.save(g);
            // save mark schmeme
            List<MarkScheme>listSchemes=new ArrayList<>();
            for (Map.Entry<String, Double> entry : model.getComponents().entrySet()) {
                System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                MarkScheme markScheme= MarkScheme.builder().markName(entry.getKey()).markWeight(entry.getValue()).status(true).subject(subject).build();
                listSchemes.add(markScheme);
            }
            markSchemeRepository.saveAll(listSchemes);
            return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), g);
        }
        return null;
    }


}
