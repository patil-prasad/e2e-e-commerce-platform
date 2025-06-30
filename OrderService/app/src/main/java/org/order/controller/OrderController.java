package org.order.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.order.exception.ForbiddenException;
import org.order.models.OrderRequestDTO;
import org.order.service.OrderService;
import org.order.util.JwtUtils;
import org.order.util.Scopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("")
    public ResponseEntity saveOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            JwtUtils.validateAccess(request, Scopes.WRITE_ORDER);
            String token = (String) request.getAttribute("token");
            String username = jwtUtils.getUserNameFromJwtToken(token);
            orderService.save(orderRequestDTO, (String)request.getAttribute("token"),username);
            return ResponseEntity.ok("Order Placed");
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("")
    public ResponseEntity getOrders(@RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            JwtUtils.validateAccess(request, Scopes.READ_ORDER);
            String token = (String) request.getAttribute("token");
            String username = jwtUtils.getUserNameFromJwtToken(token);
            return ResponseEntity.ok(orderService.getOrders(username, page, size));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
