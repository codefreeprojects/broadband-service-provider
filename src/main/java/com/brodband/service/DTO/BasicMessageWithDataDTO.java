package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class BasicMessageWithDataDTO<T> {
    private Boolean IsSuccess;
    private String Message;
    private T data;
}
