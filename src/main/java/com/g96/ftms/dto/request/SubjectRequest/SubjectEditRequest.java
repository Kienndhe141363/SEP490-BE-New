package com.g96.ftms.dto.request.SubjectRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectEditRequest extends SubjectAddRequest{
    private Long id;
}
