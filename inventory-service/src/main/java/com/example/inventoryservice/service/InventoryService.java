package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private  final InventoryRepository inventoryRepository;

    public Inventory addNewInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public Inventory updateInventory(Inventory inventory) {
        Optional<Inventory> optional = inventoryRepository.findInventoryByProductId(inventory.getProductId());
        if (optional.isPresent()) {
            if(inventory.getStock() == 0) {
                inventoryRepository.deleteInventoryByProductId(inventory.getProductId());
                return inventory;
            }
            else {
                Inventory updatedInventory = optional.get();
                updatedInventory.setStock(inventory.getStock());
                return inventoryRepository.save(updatedInventory);
            }
        }
        return null;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

}
