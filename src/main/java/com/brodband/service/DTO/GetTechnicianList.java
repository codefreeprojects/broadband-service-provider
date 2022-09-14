package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class GetTechnicianList {
    private Long TechnicianID;
    private String TechnicianName;
}
