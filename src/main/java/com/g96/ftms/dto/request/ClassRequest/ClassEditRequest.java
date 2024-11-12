package com.g96.ftms.dto.request.ClassRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassEditRequest extends ClassAddRequest{
    private Long id;
}
