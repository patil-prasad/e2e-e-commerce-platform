package org.order.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private Long id;
    private String username;
    private Double totalPrice;
    private List<OrderItemResponseDTO> orderItems;
}
