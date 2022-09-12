package com.brodband.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AdminAddressRequestDTO {
    private long userID;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String homePhone;
    private String personalNumber;
    private String email;
    private String gender;
    private String dob;
    private String occupation;
    private String companyName;
    private String position;
}
