package com.brodband.service.Controllers;

import com.brodband.service.DTO.*;
import com.brodband.service.Models.Feedback;
import com.brodband.service.Repositories.FeedbackRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api/Feedback")
public class FeedbackController {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/InsertFeedback")
    public ResponseEntity<BasicMessageDTO> insertFeedback(@RequestBody InsertFeedbackRequest insertFeedbackRequest){
        Feedback feedback = new Feedback();
        feedback.setFeedbackSummary(insertFeedbackRequest.getFeedback());
        feedback.setUserID(insertFeedbackRequest.getUserID());
        feedback.setInsertionDate(new Date());
        feedbackRepository.save(feedback);
        return new ResponseEntity<>(new BasicMessageDTO(true, "Feedback successfully added"), HttpStatus.CREATED);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/GetFeedback")
    public ResponseEntity<PaginationResponseDTO<Feedback>> getFeedback(@RequestBody PaginationDTO paginationDTO){
        Page<Feedback> feedbackPage = feedbackRepository.findAll(PageRequest.of(paginationDTO.getPageNumber() -1, paginationDTO.getNumberOfRecordPerPage()));
        return new ResponseEntity<>(new PaginationResponseDTO<>(
                true,
                "All data",
                paginationDTO.getPageNumber(),
                feedbackPage.getTotalElements(),
                Long.valueOf(feedbackPage.getTotalPages()),
                feedbackPage.stream().toList()
        ), HttpStatus.OK);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/DeleteFeedback")
    public ResponseEntity<BasicMessageDTO> deleteFeedback(@RequestBody DeleteFeedbackRequest deleteFeedbackRequest){
        feedbackRepository.deleteById(deleteFeedbackRequest.getFeedbackID());
        return new ResponseEntity<>(new BasicMessageDTO(true, "Successfully deleted"), HttpStatus.OK);
    }
}
