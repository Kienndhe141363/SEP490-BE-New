package com.g96.ftms.service.feedback;

import com.g96.ftms.dto.common.PagedResponse;
import com.g96.ftms.dto.request.FeedBackRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.dto.response.FeedBackResponse;
import com.g96.ftms.entity.FeedBack;
import com.g96.ftms.entity.FeedbackAnswer;
import com.g96.ftms.entity.User;
import com.g96.ftms.exception.AppException;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.repository.FeedBackRepository;
import com.g96.ftms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements FeedBackService {
    private final FeedBackRepository feedBackRepository;
    private final UserRepository userRepository;
    @Override
    public ApiResponse<PagedResponse<FeedBackResponse.FeedBackInfoDTO>> search(FeedBackRequest.FeedBackPagingRequest model) {
        Page<FeedBack> pages = feedBackRepository.searchFilter(model.getKeyword(), model.getUserId(), model.getSubjectId(), model.getClassId(), model.getPageable());
        List<FeedBackResponse.FeedBackInfoDTO> list = pages.getContent().stream().map(f -> {
            return FeedBackResponse
                    .FeedBackInfoDTO.builder().feedbackId(f.getFeedbackId())
                    .userId(f.getUser().getUserId()).traineeName(f.getUser().getFullName())
                    .subjectCode(f.getSubject().getSubjectCode()).avgRating(f.getAvgRating())
                    .openDate(f.getOpenTime()).lastUpdate(f.getLastUpdateTime())
                    .build();
        }).collect(Collectors.toList());
        PagedResponse<FeedBackResponse.FeedBackInfoDTO> response = new PagedResponse<>(list, pages.getNumber(), pages.getSize(), pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);
    }

    @Override
    public ApiResponse<FeedBackResponse.FeedBackFormDTO> getFeedBackFormDetail(FeedBackRequest.FeedBackDetailFormRequest model) {
        // Tìm kiếm Feedback từ feedbackId
        FeedBack feedback = feedBackRepository.findById(model.getFeedBackId())
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found for ID: " + model.getFeedBackId()));

        // Chuyển đổi danh sách câu trả lời thành QuestionAnswerFormInfoDTO
        List<FeedBackResponse.QuestionAnswerFormInfoDTO> questionAnswerForm = feedback.getFeedbackAnswerList().stream()
                .map(this::mapToQuestionAnswerFormInfoDTO)
                .collect(Collectors.toList());

        //get trainer
        User trainer = userRepository.findByAccount(feedback.getClasss().getAdmin());
        if (trainer == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.USER_NOT_FOUND);
        }
        // Tạo đối tượng FeedBackFormDTO với các trường cần thiết
        FeedBackResponse.FeedBackFormDTO response = FeedBackResponse.FeedBackFormDTO.builder()
                .feedbackId(feedback.getFeedbackId())
                .traineeName(feedback.getUser().getFullName())
                .traineeId(feedback.getUser().getUserId())
                .trainerName(trainer.getFullName())
                .trainerId(trainer.getUserId())
                .subjectCode(feedback.getSubject().getSubjectCode())
                .subjectName(feedback.getSubject().getSubjectName())
                .avgRating(feedback.getAvgRating())
                .openDate(feedback.getOpenTime())
                .description(feedback.getDescription())
                .endDate(feedback.getLastUpdateTime())
                .questionAnswerForm(questionAnswerForm)
                .build();

        return new ApiResponse<>(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), response);

    }
    private FeedBackResponse.QuestionAnswerFormInfoDTO mapToQuestionAnswerFormInfoDTO(FeedbackAnswer feedbackAnswer) {
        // Xác định câu trả lời dựa trên questionType
        String answer;
        switch (feedbackAnswer.getQuestion().getQuestionType()) {
            case RATING:
                answer = feedbackAnswer.getRating() != null ? feedbackAnswer.getRating().toString() : "No Rating";
                break;
            case YES_NO:
                answer = feedbackAnswer.getYesNoAnswer() != null ? feedbackAnswer.getYesNoAnswer().toString() : "No Answer";
                break;
            case TEXT:
                answer = feedbackAnswer.getTextAnswer() != null ? feedbackAnswer.getTextAnswer() : "No Answer";
                break;
            default:
                answer = "Unknown Type";
                break;
        }

        // Tạo QuestionAnswerFormInfoDTO với câu hỏi và câu trả lời
        return FeedBackResponse.QuestionAnswerFormInfoDTO.builder()
                .questions(feedbackAnswer.getQuestion())
                .answer(answer)
                .build();
    }
}