package com.example.orderservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPlacedEvent {
    private Long userId;
    private Long orderId;
    private Double Total;
}
