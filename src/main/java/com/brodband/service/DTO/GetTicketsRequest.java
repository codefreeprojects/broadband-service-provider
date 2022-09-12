package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTicketsRequest {
    @NotBlank
    private String TechnicianName;
    @NotBlank
    private String Type; // Active , Done
    @NotBlank
    private Integer PageNumber;
    @NotBlank
    private Integer NumberOfRecordPerPage;
}
