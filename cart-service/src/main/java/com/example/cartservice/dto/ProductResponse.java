package com.example.cartservice.dto;

import lombok.Data;

import java.util.List;

@Data
class Color {
    private String id;
    private String color;
}

@Data
class Size {
    private String id;
    private String size;
}

@Data
public class ProductResponse {
    CartProduct product;

    List<Color> colors;
    List<Size> sizes;
}
