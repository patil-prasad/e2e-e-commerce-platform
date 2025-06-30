package org.order.models;


import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private String idempotencyKey;
    private List<OrderRequestItemDTO> orderItems;
}
