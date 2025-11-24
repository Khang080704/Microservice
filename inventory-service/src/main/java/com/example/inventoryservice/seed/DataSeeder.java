package com.example.inventoryservice.seed;

import com.example.inventoryservice.dto.ProductResponse;
import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.feignclient.ProductClient;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient;

    @Override
    public void run(String... args) throws Exception {
        List<ProductResponse> products = productClient.getProducts();
        final int MIN = 1;
        final int MAX = 100;
        for (ProductResponse product : products) {
            Random random = new Random();

            Inventory  inventory = new Inventory();
            inventory.setProductId(product.getProductId());
            inventory.setStock(random.nextInt(MAX - MIN + 1) + MIN);
            inventoryRepository.save(inventory);
        }
    }
}
