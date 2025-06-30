package org.catalog.controller;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import org.catalog.entity.Product;
import org.catalog.exception.ForbiddenException;
import org.catalog.models.ProductRequestDTO;
import org.catalog.service.ProductService;
import org.catalog.util.JwtUtils;
import org.catalog.util.Scopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("")
    public ResponseEntity getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        try {
            JwtUtils.validateAccess(request, Scopes.READ_PRODUCTS);
            return ResponseEntity.ok(productService.findAll(PageRequest.of(page, size)));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity saveProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        try {
            JwtUtils.validateAccess(request, Scopes.WRITE_PRODUCTS);
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productRequestDTO));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Long id) {
        try {
            JwtUtils.validateAccess(request, Scopes.READ_PRODUCTS);
            return ResponseEntity.ok(productService.findById(id));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        try {
            return ResponseEntity.ok(productService.update(id, productRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity getProductsByCategoryId(@PathVariable String categoryName,
                                                  @RequestParam(defaultValue = "0",name = "page") int page,
                                                  @RequestParam(defaultValue = "10", name = "size") int size) {
        try {
            JwtUtils.validateAccess(request, Scopes.READ_PRODUCTS);
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(productService.findByCategoryName(categoryName.toUpperCase(Locale.ENGLISH), pageable));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/buy/{id}")
    public ResponseEntity buyProduct(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            JwtUtils.validateAccess(request, Scopes.WRITE_PRODUCTS);
            return ResponseEntity.ok(productService.buyProduct(id, quantity));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Product Out of Stock");
        }
    }


}
