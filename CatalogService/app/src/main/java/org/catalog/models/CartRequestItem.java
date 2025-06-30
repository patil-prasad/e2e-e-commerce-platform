package org.catalog.models;

import lombok.Getter;

@Getter
public class CartRequestItem {
    private Long cartItemId;
    private Long productId;
    private Integer quantity;
}
