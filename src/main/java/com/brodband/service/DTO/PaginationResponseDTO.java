package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class PaginationResponseDTO<T> {
    private Boolean isSuccess;
    private String message;
    private Integer currentPage;
    private Long totalRecords;
    private Long totalPage;
    private List<T> data;
}
