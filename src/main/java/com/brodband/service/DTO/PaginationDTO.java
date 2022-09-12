package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class PaginationDTO {
    private Integer pageNumber;
    private Integer numberOfRecordPerPage;
}
