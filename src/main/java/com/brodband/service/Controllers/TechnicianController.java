package com.brodband.service.Controllers;

import com.brodband.service.DTO.*;
import com.brodband.service.Models.Address;
import com.brodband.service.Models.Ticket;
import com.brodband.service.Repositories.AddressRepository;
import com.brodband.service.Repositories.TicketRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/Technician")
public class TechnicianController {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    TicketRepository ticketRepository;

    @Operation(summary = "Used to update address", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/UpdateTechnicianAddress")
    public ResponseEntity<BasicMessageDTO> updateAddress(@RequestBody AdminAddressRequestDTO r) {
        Optional<Address> address = addressRepository.getAddressByUserID(r.getUserID());
        Address a;
        if(!address.isPresent()) {
            a = new Address();
            //return new ResponseEntity<>(new BasicMessageDTO(false, "User information not found"), HttpStatus.OK);
        } else {
            a = address.get();
        }

        a.setUserID(r.getUserID());
        a.setFirstName(r.getFirstName());
        a.setLastName(r.getLastName());
        a.setAddress(r.getAddress());
        a.setCity(r.getCity());
        a.setState(r.getState());
        a.setZipCode(r.getZipCode());
        a.setHomePhone(r.getHomePhone());
        a.setPersonalNumber(r.getPersonalNumber());
        a.setEmail(r.getEmail());
        a.setGender(r.getGender());
        a.setDob(r.getDob());
        a.setOccupation(r.getOccupation());
        a.setCompanyName(r.getCompanyName());
        a.setPosition(r.getPosition());
        addressRepository.save(a);
        return new ResponseEntity<>(new BasicMessageDTO(true, "Address Updated"), HttpStatus.OK);
    }

    @Operation(summary = "Get address by userid", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/GetTechnicianAddress")
    public ResponseEntity<BasicMessageWithDataDTO<Address>> getAddress(Long userId) {
        Optional<Address> address = addressRepository.getAddressByUserID(userId);
        if(address.isPresent()) {
            return new ResponseEntity<>(
                    new BasicMessageWithDataDTO<>(
                            true,
                            "Address found",
                            address.get()),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(new BasicMessageWithDataDTO<>(false, "Address not found", null), HttpStatus.OK);
    }

    @Operation(summary = "Get address by userid", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/GetTickets")
    public ResponseEntity<PaginationResponseDTO<Ticket>> getTickets(@RequestBody GetTicketsRequest getTicketsRequest) {
        Page<Ticket> ticketPage = ticketRepository.findAllByAssignerIgnoreCaseAndPlanType(
                getTicketsRequest.getTechnicianName(),
                getTicketsRequest.getType(),
                PageRequest.of(getTicketsRequest.getPageNumber() -1, getTicketsRequest.getNumberOfRecordPerPage()));
        return new ResponseEntity<>(new PaginationResponseDTO<>(
                true,
                "all data",
                getTicketsRequest.getPageNumber(),
                (long) ticketPage.getNumberOfElements(),
                (long) ticketPage.getTotalPages(),
                ticketPage.stream().toList()
        ), HttpStatus.OK);
    }
}
