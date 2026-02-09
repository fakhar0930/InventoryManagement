package com.inventorymanagement.Repository;

import com.inventorymanagement.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Integer> {

    Optional<ProductEntity> findByProductName(String name);

}
