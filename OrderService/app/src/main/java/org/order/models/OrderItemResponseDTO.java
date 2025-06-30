package org.order.models;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double price;
}
