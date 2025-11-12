package com.example.notificationservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
    private Double total;
}
