package com.g96.ftms.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
public class PagingBaseParams {
    private int page = 0; // default value
    private int size = 10; // default value
    private String orderBy; // default value
    private String sortDirection; // default value

    public PagingBaseParams(int page, int size, String orderBy, String sortDirection) {
        this.page = page;
        this.size = size;
        this.orderBy = orderBy;
        this.sortDirection = sortDirection;
    }

    public Pageable getPageable() {
        if (StringUtils.isEmpty(sortDirection) || StringUtils.isEmpty(orderBy)) {
            return PageRequest.of(page, size, Sort.Direction.DESC, "id");
        }
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, size, Sort.by(direction, orderBy));
    }

    public Sort.Direction getDirection() {
        return "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

}