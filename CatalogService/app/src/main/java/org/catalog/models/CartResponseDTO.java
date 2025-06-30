package org.catalog.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartResponseDTO {

    public String username;
    public Long id;
    public List<CartResponseItem> items;
}
