package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequestDTO {

    private String ProductName;
    private String ProductDescription;
    private String ProductPrice;
    private String ProductType;
    private MultipartFile File;
}
