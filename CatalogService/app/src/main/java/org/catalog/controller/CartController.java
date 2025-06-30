package org.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.catalog.exception.ForbiddenException;
import org.catalog.models.CartRequestDTO;
import org.catalog.models.CartRequestItem;
import org.catalog.models.CartResponseDTO;
import org.catalog.models.CategoryResponseDTO;
import org.catalog.service.CartItemService;
import org.catalog.service.CartService;
import org.catalog.util.JwtUtils;
import org.catalog.util.Scopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("")
    public ResponseEntity getCart() {
        try {
            JwtUtils.validateAccess(request, Scopes.READ_CART);
            String username = JwtUtils.getUsernameFromToken(request);
            CartResponseDTO cart =  cartService.findByUsername(username);
            return ResponseEntity.ok(cart);
        }catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity createCart(@RequestBody CartRequestDTO cartRequestDTO) {
        try {
            JwtUtils.validateAccess(request, Scopes.WRITE_CART);
            String username = JwtUtils.getUsernameFromToken(request);
            cartService.save(cartRequestDTO,username);
            return ResponseEntity.created( null).body("Created cart");
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("")
    public ResponseEntity updateCart(@RequestBody CartRequestItem cartRequestItem) {
        try {
            JwtUtils.validateAccess(request, Scopes.WRITE_CART);
            cartItemService.updateCartItem(cartRequestItem);
            return ResponseEntity.created( null).body("Updated cart item");
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteCart(@RequestBody CartRequestItem cartRequestItem) {
        try {
            JwtUtils.validateAccess(request, Scopes.WRITE_CART);
            cartItemService.deleteCartItem(cartRequestItem.getCartItemId());
            return ResponseEntity.created( null).body("Deleted cart item");
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
