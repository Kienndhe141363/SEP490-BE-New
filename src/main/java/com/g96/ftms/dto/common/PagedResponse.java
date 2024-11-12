package com.g96.ftms.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PagedResponse<T> {
    private List<T> dataSource;
    public PaginationParams pagination;

    public PagedResponse(List<T> dataSource, PaginationParams pagination) {
        this.dataSource = dataSource;
        this.pagination = pagination;
    }

    public PagedResponse(List<T> dataSource, int page, int size, long totalElements, int totalPages, boolean last) {
        this.dataSource = dataSource;
        pagination = new PaginationParams(page, size, totalElements, totalPages, last);
    }

    public <S> PagedResponse<S> map(Function<? super T, ? extends S> mapper) {
        return new PagedResponse(dataSource.stream().map(mapper).collect(Collectors.toList()), this.pagination);
    }

    @Data
    @NoArgsConstructor
    public class PaginationParams {
        private int page;
        private int size;
        long totalElements;
        private int totalPages;
        private boolean last;

        public PaginationParams(int page, int size, long totalElements, int totalPages, boolean last) {
            this.page = page;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.last = last;
        }
    }
}