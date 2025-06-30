package org.catalog.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

@Getter
public enum Scopes {
    READ_PRODUCTS("read:products"),
    WRITE_PRODUCTS("write:products"),
    READ_CATEGORIES("read:categories"),
    WRITE_CATEGORIES("write:categories"),
    WRITE_CART("write:cart"),
    READ_CART("read:cart");


    private final String scope;

    Scopes(String scope) {
        this.scope = scope;
    }

}
