package com.brodband.service.Controllers;

import com.brodband.service.DTO.BasicMessageDTO;
import com.brodband.service.DTO.SignInRequestDTO;
import com.brodband.service.DTO.SignInResponseDTO;
import com.brodband.service.DTO.SignUpRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthController {
    public ResponseEntity<SignInResponseDTO> signIn(SignInRequestDTO signInRequestDTO);
    public ResponseEntity<BasicMessageDTO> signUp(SignUpRequestDTO signUpRequestDTO);
}
