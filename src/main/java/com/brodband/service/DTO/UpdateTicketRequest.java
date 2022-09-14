package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data @NoArgsConstructor @AllArgsConstructor
public class UpdateTicketRequest {
    @NotBlank
    private Long TicketID ;

        @NotBlank
    private Long UserID ;

        @NotBlank
    private String City ;
        @NotBlank
    private String Assigner ;
        @NotBlank
    private String Reportor ;

        @NotBlank
    private String PlanType ;

        @NotBlank
    private String RaiseType ;

        @NotBlank
    private String Summary ;

    private String Description ;

    private String Status ;
}
