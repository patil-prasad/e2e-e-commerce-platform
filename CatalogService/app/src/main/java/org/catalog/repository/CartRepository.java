package org.catalog.repository;


import org.catalog.entity.Cart;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.username = ?1")
    @EntityGraph(attributePaths = {"cartItemList"})
    Cart findByUsername(String username);
}
