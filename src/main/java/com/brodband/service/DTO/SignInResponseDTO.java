package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDTO {
    private Boolean IsSuccess;
    private String Message;
    private UserInfoDTO data;
    private String Token;
}
