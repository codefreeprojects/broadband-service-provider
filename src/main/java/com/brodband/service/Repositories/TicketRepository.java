package com.brodband.service.Repositories;

import com.brodband.service.Models.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findAllByAssignerIgnoreCaseAndPlanType(String assigner, String planType, Pageable pageable);
}
