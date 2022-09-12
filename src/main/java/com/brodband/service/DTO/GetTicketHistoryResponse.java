package com.brodband.service.DTO;

import com.brodband.service.Models.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetTicketHistoryResponse {
    private Boolean IsSuccess;
    private String Message;
    private Integer CurrentPage;
    private Double TotalRecords;
    private Integer TotalPage;
    private List<Ticket> data;
}
