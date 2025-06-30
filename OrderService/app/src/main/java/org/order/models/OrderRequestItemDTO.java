package org.order.models;


import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class OrderRequestItemDTO {
    private Long productId;
    private Integer quantity;
    private Double price;
}
