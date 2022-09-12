package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data @NoArgsConstructor @AllArgsConstructor
public class UpdateProductRequestDTO {
    private Long ProductId;
    private String ProductName;
    private String ProductDescription;
    private String ProductPrice;
    private String ProductType;
    private String PublicID;
    private String ImageUrl;
    private MultipartFile File;
}
