package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor @NoArgsConstructor @Data
public class PurchaseProductRequestDTO {
    @NotBlank
    private Long UserID;
    @NotBlank
    private Long ProductID;
    @NotBlank
    private String ProductType;
}
