package com.example.inventoryservice.controller;

import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<?> addInventory(@RequestBody Inventory inventory){
        Inventory savedInventory = inventoryService.addNewInventory(inventory);
        return ResponseEntity.ok().body(savedInventory);
    }

    @GetMapping
    public ResponseEntity<?> getInventory(){
        return ResponseEntity.ok().body(inventoryService.getAllInventory());
    }

    @PutMapping
    public ResponseEntity<?> updateInventory(@RequestBody Inventory inventory){
        Inventory savedInventory = inventoryService.updateInventory(inventory);
        return ResponseEntity.ok().body(savedInventory);
    }
}
