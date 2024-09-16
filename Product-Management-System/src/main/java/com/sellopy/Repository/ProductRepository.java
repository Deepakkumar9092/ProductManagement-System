package com.sellopy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sellopy.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
