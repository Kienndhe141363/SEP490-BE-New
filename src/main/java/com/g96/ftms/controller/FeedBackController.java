package com.g96.ftms.controller;

import com.g96.ftms.dto.request.FeedBackRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.service.feedback.FeedBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback-management")
@RequiredArgsConstructor
public class FeedBackController {
    private final FeedBackService feedBackService;
    @PostMapping("/search")
    public ApiResponse<?> getFeedbackList(@RequestBody FeedBackRequest.FeedBackPagingRequest model) {
        return feedBackService.search(model);
    }
    @PostMapping("/detail")
    public ApiResponse<?> getFeedBackFormDetail(@PathVariable FeedBackRequest.FeedBackDetailFormRequest model) {
        return feedBackService.getFeedBackFormDetail(model);
    }
}
