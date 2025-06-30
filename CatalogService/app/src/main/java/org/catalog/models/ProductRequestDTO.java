package org.catalog.models;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private Long categoryId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
