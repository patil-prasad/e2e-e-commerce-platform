package org.catalog.models;

import lombok.Data;
import lombok.Getter;

import java.util.List;

;

@Getter
public class CartRequestDTO {
    public List<CartRequestItem> items;
}
