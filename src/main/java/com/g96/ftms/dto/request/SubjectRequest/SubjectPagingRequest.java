package com.g96.ftms.dto.request.SubjectRequest;

import com.g96.ftms.dto.common.PagingBaseParams;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectPagingRequest extends PagingBaseParams{
        private String keyword;
        private Boolean status;
}