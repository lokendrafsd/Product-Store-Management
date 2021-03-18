package com.shopapplication.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopapplication.services.model.ProductRequest;

@Repository
public interface ProductRepository extends JpaRepository<ProductRequest, Long> {

}
