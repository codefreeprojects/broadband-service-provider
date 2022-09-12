package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertFeedbackRequest {
    @NotBlank
    private Long UserID;
    @NotBlank
    private String Feedback;
}
