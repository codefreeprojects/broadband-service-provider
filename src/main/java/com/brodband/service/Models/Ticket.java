package com.brodband.service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TicketID;
    private Date InsertionDate;
    private Long UserID;
    private String City;
    private String Reportor;
    private String Assigner;
    private String PlanType;
    private String RaiseType;
    private String Summary;
    private String Description;
    private String Status;

}
