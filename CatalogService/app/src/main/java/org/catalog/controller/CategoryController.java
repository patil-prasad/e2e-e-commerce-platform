package org.catalog.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.catalog.entity.Category;
import org.catalog.exception.ForbiddenException;
import org.catalog.models.CategoryRequestDTO;
import org.catalog.models.CategoryResponseDTO;
import org.catalog.service.CategoryService;
import org.catalog.util.JwtUtils;
import org.catalog.util.Scopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HttpServletRequest request;


    @PostMapping("")
    public ResponseEntity saveCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        try {
            JwtUtils.validateAccess(request, Scopes.WRITE_CATEGORIES);
            CategoryResponseDTO category = categoryService.save(categoryRequestDTO);
            return ResponseEntity.ok(category);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity getAllCategories(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            JwtUtils.validateAccess(request, Scopes.READ_CATEGORIES);
            return ResponseEntity.ok(categoryService.findAll(PageRequest.of(page, size)));
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("")
    public ResponseEntity updateCategory(@RequestParam("id") Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        try {
            JwtUtils.validateAccess(request, Scopes.WRITE_CATEGORIES);
            CategoryResponseDTO category = categoryService.update(id, categoryRequestDTO);
            return ResponseEntity.ok(category);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
