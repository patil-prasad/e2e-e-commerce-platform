package org.catalog.service;

import org.catalog.entity.Cart;
import org.catalog.entity.CartItem;
import org.catalog.models.*;
import org.catalog.repository.CartItemRepository;
import org.catalog.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    @Transactional
    public CartResponseItem updateCartItem(CartRequestItem cartItemRequestDTO) {
        CartItem cartItem = cartItemRepository.findById(cartItemRequestDTO.getCartItemId())
                .orElseThrow(() -> new NoSuchElementException("Cart item not found"));
        cartItem.setQuantity(cartItemRequestDTO.getQuantity());
        cartItemRepository.save(cartItem);
        return CartResponseItem
                .builder()
                .cartId(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .product(modelMapper
                        .map(productService.findById(cartItem.getProductId()), ProductResponseDTO.class)
                )
                .build();
    }

    @Transactional
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
