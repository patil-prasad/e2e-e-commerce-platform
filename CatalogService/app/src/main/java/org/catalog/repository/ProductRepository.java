package org.catalog.repository;

import org.catalog.entity.Category;
import org.catalog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public Page<Product> findByCategoryName(String name, Pageable pageable);

}
