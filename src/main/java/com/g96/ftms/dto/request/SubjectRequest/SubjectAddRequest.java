package com.g96.ftms.dto.request.SubjectRequest;

import com.g96.ftms.dto.response.SchemeResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SubjectAddRequest {
    private String subjectName;
    private String subjectCode;
    private String documentLink;
    private String descriptions;
    private boolean status;
    List<SchemeResponse.SubjectSchemeInfo>schemes;
}
