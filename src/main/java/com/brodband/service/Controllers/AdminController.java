package com.brodband.service.Controllers;

import com.brodband.service.DTO.*;
import com.brodband.service.Models.Address;
import com.brodband.service.Models.Product;
import com.brodband.service.Models.Ticket;
import com.brodband.service.Models.User;
import com.brodband.service.Repositories.AddressRepository;
import com.brodband.service.Repositories.ProductRepository;
import com.brodband.service.Repositories.TicketRepository;
import com.brodband.service.Repositories.UserRepository;
import com.brodband.service.Services.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/Admin")
public class AdminController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;

    @Operation(summary = "Used to update address", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/UpdateAdminAddress")
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
    @GetMapping("/GetAdminAddress")
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

    @Operation(summary = "Insert new product", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/AddProduct", method = RequestMethod.POST, consumes="multipart/form-data")
    public ResponseEntity<BasicMessageDTO> addProduct(@ModelAttribute  AddProductRequestDTO r)  {
        try {
            FileUploadReturnDTO fileReturn = fileUploadService.uploadFile(r.getFile());
            Product product = new Product();
            product.setProductDescription(r.getProductDescription());
            product.setProductName(r.getProductName());
            product.setProductPrice(r.getProductPrice());
            product.setProductType(r.getProductType());
            product.setInsertionDate(new Date());
            product.setProductImageUrl(fileReturn.getImageUrl());
            product.setPublicID(fileReturn.getPublicId());
            productRepository.save(product);
            return new ResponseEntity<>( new BasicMessageDTO(true, "Successfully product added"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicMessageDTO(false, "Something went wrong"), HttpStatus.OK);
        }

    }

    @Operation(summary = "Fetch all products", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/GetAllProduct")
    public ResponseEntity<PaginationResponseDTO<Product>> getAllProducts(@RequestBody PaginationDTO paginationDTO) {
        List<Product> products = productRepository.findAll(PageRequest.of((paginationDTO.getPageNumber() - 1) ,  paginationDTO.getNumberOfRecordPerPage())).stream().toList();

        return new ResponseEntity<>( new PaginationResponseDTO<>(
                true,
                "Data",
                paginationDTO.getPageNumber(),
                productRepository.count(),
                productRepository.count() > 0 && productRepository.count() < paginationDTO.getNumberOfRecordPerPage() ? 1L : productRepository.count() / paginationDTO.getNumberOfRecordPerPage(),
                products
        ), HttpStatus.OK);
    }
    @Operation(summary = "Delete product", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/DeleteProduct")
    public ResponseEntity<BasicMessageDTO> deleteProduct(@RequestBody DeleteProductRequestDTO r) {
        productRepository.deleteById(r.getProductId());
        return new ResponseEntity<>( new BasicMessageDTO(true, "Product deleted")
        , HttpStatus.OK);
    }

    @Operation(summary = "Update product", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/UpdateProduct", method = RequestMethod.PATCH, consumes="multipart/form-data")
    @Transactional
    public ResponseEntity<BasicMessageDTO> updateProduct(@ModelAttribute UpdateProductRequestDTO r) {
        Optional<Product> _product = productRepository.findById(r.getProductId());
        if(_product.isPresent()){
            try{
                FileUploadReturnDTO fileUploadReturnDTO = fileUploadService.deleteAndUploadFile(r.getPublicID(), r.getFile());
                Product product = _product.get();
                product.setProductName(r.getProductName());
                product.setProductDescription(r.getProductDescription());
                product.setProductType(r.getProductType());
                product.setProductPrice(r.getProductPrice());
                //image params set delete image and set it.
                product.setPublicID(fileUploadReturnDTO.getPublicId());
                product.setProductImageUrl(fileUploadReturnDTO.getImageUrl());
                return new ResponseEntity<>( new BasicMessageDTO(true, "Product updated")
                        , HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<>( new BasicMessageDTO(false, "File upload error")
                        , HttpStatus.OK);
            }

        }
        return new ResponseEntity<>( new BasicMessageDTO(false, "Product not found")
                , HttpStatus.NO_CONTENT);
    }
    @Operation( security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/GetTechnicianList")
    public ResponseEntity<BasicMessageWithDataDTO<GetTechnicianList>> getTechnicianList(@RequestBody GetTechnicianListRequest r) {
        BasicMessageWithDataDTO response = new BasicMessageWithDataDTO();

        response.setIsSuccess(true);
        response.setMessage("");
        List<GetTechnicianList> getTechnicianLists =  userRepository.findAllByRoleIgnoreCase("technician").stream().map(user-> {
            Optional<Address> address = addressRepository.findByCityIgnoreCaseAndUserID(r.getCity(),user.getUserId());
            if(address.isPresent()){
                GetTechnicianList getTechnicianList = new GetTechnicianList();
                getTechnicianList.setTechnicianID(user.getUserId());
                getTechnicianList.setTechnicianName(user.getFirstName() + " "+user.getLastName());
                return getTechnicianList;
            }
            return null;
        }).filter(item-> item != null).toList();
        response.setData(getTechnicianLists);
        return new ResponseEntity<>( response, HttpStatus.OK);
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/UpdateTicket")
    public ResponseEntity<BasicMessageDTO> deleteProduct(@RequestBody UpdateTicketRequest r) {
        BasicMessageDTO basicMessageDTO = new BasicMessageDTO(true, "Update Ticket Successfully");
        Optional<Ticket> ticket = ticketRepository.findById(r.getTicketID());
        if(ticket.isPresent()){
            Ticket t = ticket.get();
            t.setStatus(r.getStatus());
            t.setAssigner(r.getAssigner());
            t.setCity(r.getCity());
            t.setDescription(r.getDescription());
            t.setUserID(r.getUserID());
            t.setReportor(r.getReportor());
            t.setPlanType(r.getPlanType());
            t.setRaiseType(r.getRaiseType());
            t.setSummary(r.getSummary());
            ticketRepository.save(t);
        } else {
            basicMessageDTO.setMessage("Ticket not found");
            basicMessageDTO.setIsSuccess(false);
        }
        return new ResponseEntity<>(basicMessageDTO, HttpStatus.OK);
    }
}
