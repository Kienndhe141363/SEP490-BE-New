package com.g96.ftms.dto.request.ClassRequest;

import com.g96.ftms.dto.common.PagingBaseParams;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassPagingRequest extends PagingBaseParams {
    private String keyword;
    private Boolean status;
}