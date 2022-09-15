package com.brodband.service.Controllers;

import com.brodband.service.DTO.*;
import com.brodband.service.Models.Address;
import com.brodband.service.Models.PurchaseProduct;
import com.brodband.service.Models.Ticket;
import com.brodband.service.Repositories.AddressRepository;
import com.brodband.service.Repositories.PurchaseProductRepository;
import com.brodband.service.Repositories.TicketRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/Customer")
public class CustomerController {
    private ModelMapper mapper = new ModelMapper();
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    PurchaseProductRepository purchaseProductRepository;

    @Operation(summary = "Used to update address", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/UpdateCustomerAddress")
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
    @GetMapping("/GetCustomerAddress")
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

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/CreateTicket")
    public ResponseEntity<BasicMessageDTO> createTicket(@RequestBody CreateTicketRequestDTO createTicketRequestDTO){
        Ticket ticket = this.mapper.map(createTicketRequestDTO, Ticket.class);
        ticket.setInsertionDate(new Date());
        ticket.setAssigner("admin");
        ticket.setStatus("pending");
        ticketRepository.save(ticket);
        return new ResponseEntity<>(new BasicMessageDTO(true, "Raise Ticket Successfully"), HttpStatus.CREATED);
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/GetTicketHistory")
    public ResponseEntity<PaginationResponseDTO<Ticket>> getTicketHistory(@RequestBody PaginationWithUserDTO paginationWithUserDTO){
        Page<Ticket> ticketPage = ticketRepository.findAll(PageRequest.of(paginationWithUserDTO.getPageNumber() -1, paginationWithUserDTO.getNumberOfRecordPerPage()));
        return new ResponseEntity<>(new PaginationResponseDTO<>(
                true,
                "all data",
                paginationWithUserDTO.getPageNumber(),
                Long.valueOf(ticketPage.getNumberOfElements()),
                Long.valueOf(ticketPage.getTotalPages()),
                ticketPage.stream().toList()
        ), HttpStatus.OK);
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/PurchaseProduct")
    public ResponseEntity<BasicMessageDTO> purchaseProduct(@RequestBody PurchaseProductRequestDTO purchaseProductRequestDTO){
        BasicMessageDTO basicMessageDTO = new BasicMessageDTO(true, "Purchase Product Successfully");
        PurchaseProduct purchaseProduct = new PurchaseProduct();
        purchaseProduct.setProductID(purchaseProductRequestDTO.getProductID());
        purchaseProduct.setProductType(purchaseProductRequestDTO.getProductType());
        purchaseProduct.setUserID(purchaseProductRequestDTO.getUserID());
        purchaseProduct.setInsertionDate(new Date());
        purchaseProductRepository.save(purchaseProduct);
        return new ResponseEntity<>(basicMessageDTO, HttpStatus.OK);
    }


    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/GetPurchaseProduct")
    public ResponseEntity<PaginationResponseDTO<PurchaseProduct>> getPurchaseProduct(@RequestBody GetPurchaseProductRequestDTO r){
        Page<PurchaseProduct> purchaseProducts = purchaseProductRepository.findAll(  PageRequest.of(r.getPageNumber() -1, r.getNumberOfRecordPerPage()));
        return new ResponseEntity<>(new PaginationResponseDTO<>(
                true,
                "all data",
                r.getPageNumber(),
                Long.valueOf(purchaseProducts.getNumberOfElements()),
                Long.valueOf(purchaseProducts.getTotalPages()),
                purchaseProducts.stream().filter(
                        item-> (item.getUserID().equals(r.getUserID()) && item.getProductType().equalsIgnoreCase(r.getProductType()))
                ).toList()
        ), HttpStatus.OK);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/DeleteTicketHistory")
    public ResponseEntity<BasicMessageDTO> deleteTicketHistory(@RequestBody DeleteTicketHistoryRequest deleteTicketHistoryRequest){
        ticketRepository.deleteById(deleteTicketHistoryRequest.getTicketID());
        return new ResponseEntity<>(new BasicMessageDTO(true, "Ticket successfully deleted"), HttpStatus.OK);
    }
}
