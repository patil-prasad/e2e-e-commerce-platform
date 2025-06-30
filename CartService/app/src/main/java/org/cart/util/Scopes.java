package org.cart.util;

import lombok.Getter;

@Getter
public enum Scopes {
    READ_PRODUCTS("read:products"),
    WRITE_PRODUCTS("write:products"),
    READ_CATEGORIES("read:categories"),
    WRITE_CATEGORIES("write:categories");

    private final String scope;

    Scopes(String scope) {
        this.scope = scope;
    }

}
