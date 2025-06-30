package org.catalog.service;

import org.catalog.entity.Cart;
import org.catalog.entity.CartItem;
import org.catalog.models.*;
import org.catalog.repository.CartItemRepository;
import org.catalog.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    public CartResponseDTO findByUsername(String username) {
        Cart cart = cartRepository.findByUsername(username);
        if(cart == null) {
            throw new NoSuchElementException("Cart not found");
        }
        System.out.println(cart);
        List<CartResponseItem> cartItems = cart.getCartItemList()
                .stream()
                .map(cartItem -> CartResponseItem
                    .builder()
                    .cartId(cartItem.getId())
                    .quantity(cartItem.getQuantity())
                    .product(modelMapper
                            .map(productService.findById(cartItem.getProductId()), ProductResponseDTO.class)
                    )
                    .build()
                )
                    .collect(Collectors.toList());
        CartResponseDTO cartResponseDTO = new CartResponseDTO(cart.getUsername(), cart.getId(), cartItems);
        return cartResponseDTO;
    }

    @Transactional
    public Boolean save(CartRequestDTO cartRequestDTO, String username) {
        Cart cart = cartRepository.findByUsername(username);
        if(cart == null) {
            Cart userCart = Cart.builder()
                    .username(username)
                    .build();
            cart = cartRepository.save(userCart);
        }
        Cart finalCart = cart;
        cartRequestDTO.getItems().forEach((CartRequestItem cartItemRequestDTO) -> {
            CartItem cartItem = CartItem.builder()
                    .cart(finalCart)
                    .productId(cartItemRequestDTO.getProductId())
                    .quantity(cartItemRequestDTO.getQuantity())
                    .build();
            try {
                cartItemRepository.save(cartItem);
            }catch (DataIntegrityViolationException e) {
                CartItem existingCartItem = cartItemRepository.findByCartIdAndProductId(finalCart.getId(), cartItemRequestDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product already exists in the cart but couldn't be found."));
                existingCartItem.setQuantity(cartItemRequestDTO.getQuantity());
                cartItemRepository.save(existingCartItem);
            }
        });
        return true;
    }
}
