package com.example.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "color")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Color {
    @Id
    private String id;
    private String color;
}
