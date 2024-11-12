package com.g96.ftms.dto.request.ClassRequest;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public  class ClassAddRequest {
    private String classCode;
    private Long roomId;
    private Long curriculumId;
    private String admin;
    private String descriptions;
    private Boolean status;
    private LocalDateTime endDate ;
    private LocalDateTime startDate;
}
