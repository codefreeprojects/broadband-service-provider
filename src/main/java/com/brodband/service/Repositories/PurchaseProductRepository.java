package com.brodband.service.Repositories;

import com.brodband.service.Models.PurchaseProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Long> {
    Page<PurchaseProduct> findAllByProductType(String type, Pageable pageable);
}
