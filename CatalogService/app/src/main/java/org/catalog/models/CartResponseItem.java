package org.catalog.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponseItem {
    private Long cartId;
    private ProductResponseDTO product;
    private Integer quantity;
}
