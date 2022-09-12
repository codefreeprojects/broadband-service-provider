package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPurchaseProductRequestDTO {
    @NotBlank
    private Integer PageNumber;
    @NotBlank
    private Integer NumberOfRecordPerPage;
    @NotBlank
    private Long UserID;
    @NotBlank
    private String ProductType; // all, device, plan
}
