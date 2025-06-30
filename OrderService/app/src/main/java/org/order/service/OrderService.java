package org.order.service;


import org.order.entity.Order;
import org.order.entity.OrderItem;
import org.order.exception.ForbiddenException;
import org.order.models.OrderItemResponseDTO;
import org.order.models.OrderRequestDTO;
import org.order.models.OrderRequestItemDTO;
import org.order.models.OrderResponseDTO;
import org.order.repository.OrderItemRepository;
import org.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public void save(OrderRequestDTO orderRequestDTO, String token, String username) throws ForbiddenException {
        for (OrderRequestItemDTO orderRequestItemDTO : orderRequestDTO.getOrderItems()) {
            if (orderRequestItemDTO.getProductId() == null || orderRequestItemDTO.getQuantity() <= 0) {
                throw new IllegalArgumentException("Invalid cart item");
            }
            catalogService.buyProduct(orderRequestItemDTO.getProductId(), orderRequestItemDTO.getQuantity(), token)
                    .orElseThrow(() -> new ForbiddenException("Product not available"));

            orderRequestItemDTO.setPrice(catalogService.getProductPrice(orderRequestItemDTO.getProductId(), token));
        }


        Order order = Order
                .builder()
                .username(username)
                .totalPrice(orderRequestDTO.getOrderItems().stream()
                                .mapToDouble(ri -> ri.getPrice() * ri.getQuantity())
                        .sum()
                )
                .build();
        Order savedOrder = orderRepository.save(order);

        orderRequestDTO.getOrderItems().forEach(ri -> {
            OrderItem orderItem = OrderItem
                    .builder()
                    .order(savedOrder)
                    .productId(ri.getProductId())
                    .quantity(ri.getQuantity())
                    .price(catalogService.getProductPrice(ri.getProductId(),token))
                    .build();
            orderItemRepository.save(orderItem);
        });
    }

    public Page<OrderResponseDTO> getOrders(String username, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be positive");
        }

        Page<Order> orderPage = orderRepository.findAll(PageRequest.of(page, size));

        if (orderPage.isEmpty()) {
            return Page.empty();
        }

        return orderPage.map(order -> {
            return OrderResponseDTO
                    .builder()
                    .id(order.getId())
                    .username(order.getUsername())
                    .totalPrice(order.getTotalPrice())
                    .orderItems(order.getOrderItems().stream().map(oi -> {
                        if (oi.getProductId() == null || oi.getQuantity() <= 0) {
                            throw new IllegalArgumentException("Product id and quantity must be positive");
                        }

                        return OrderItemResponseDTO
                                .builder()
                                .id(oi.getId())
                                .productId(oi.getProductId())
                                .quantity(oi.getQuantity())
                                .price(oi.getPrice())
                                .build();
                    }).toList())
                    .build();
        });
    }
}
