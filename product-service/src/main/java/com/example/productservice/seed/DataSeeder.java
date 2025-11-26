package com.example.productservice.seed;

import com.example.productservice.model.*;
import com.example.productservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if(productRepository.count() != 0) {
            return;
        }


            // 1. Category seeding

            // Electronics & Computers
            Category smartPhone = Category.builder().categoryName("Smart Phone").build();
            Category laptop = Category.builder().categoryName("Laptop").build();
            Category tablet = Category.builder().categoryName("Tablet").build();
            Category headphone = Category.builder().categoryName("Headphone").build();
            Category smartWatch = Category.builder().categoryName("Smart Watch").build();
            Category tv = Category.builder().categoryName("Television").build();
            Category camera = Category.builder().categoryName("Camera").build();
            Category desktopPC = Category.builder().categoryName("Desktop PC").build();

            // Fashion & Apparel
            Category sneaker = Category.builder().categoryName("Sneaker").build();
            Category bag = Category.builder().categoryName("Bag").build();
            Category clothes = Category.builder().categoryName("Clothes").build();
            Category tShirt = Category.builder().categoryName("T-Shirt").build();
            Category jacket = Category.builder().categoryName("Jacket").build();
            Category jewelry = Category.builder().categoryName("Jewelry").build();

            // Home & Living
            Category furniture = Category.builder().categoryName("Furniture").build();
            Category kitchenAppliances = Category.builder().categoryName("Kitchen Appliances").build();
            Category homeDecor = Category.builder().categoryName("Home Decor").build();

            // Others
            Category books = Category.builder().categoryName("Books").build();
            Category sportsEquipment = Category.builder().categoryName("Sports Equipment").build();
            Category toys = Category.builder().categoryName("Toys").build();

            categoryRepository.saveAll(List.of(smartPhone,laptop, bag, sneaker,clothes, tablet
            , headphone, smartWatch, tv, camera, desktopPC, tShirt, jacket, jewelry, furniture
            , kitchenAppliances, homeDecor, books, sportsEquipment, toys));
            log.info("Đã seed {} danh mục.", categoryRepository.count());

            // 2. Brand seeding
            Brand apple = Brand.builder().name("Apple").build();
            Brand samsung = Brand.builder().name("Samsung").build();
            Brand hp = Brand.builder().name("HP").build();
            Brand dell = Brand.builder().name("Dell").build();
            Brand sony = Brand.builder().name("Sony").build();
            Brand lg = Brand.builder().name("LG").build();
            Brand nike = Brand.builder().name("Nike").build();
            Brand adidas = Brand.builder().name("Adidas").build();
            Brand gucci = Brand.builder().name("Gucci").build();
            Brand ikea = Brand.builder().name("IKEA").build();
            Brand prenticeHall = Brand.builder().name("Prentice Hall").build();

            brandRepository.saveAll(List.of(apple,samsung,hp,dell, sony, lg, nike, adidas
            , gucci,ikea, prenticeHall));
            log.info("Đã seed {} thương hiệu.", brandRepository.count());


            // 3. Product seeding
            // Product 1
            Product pr1 = new Product();
            pr1.setProductName("Iphone 15 pro max");
            pr1.setBrand(apple);
            pr1.setCategory(smartPhone);
            pr1.setPrice(150.500);
            pr1.setDescription(pr1.getProductName() + " is the best");

            Product pr2 = new Product();
            pr2.setProductName("Samsung Galaxy S24 Ultra");
            pr2.setBrand(samsung);
            pr2.setCategory(smartPhone);
            pr2.setPrice(240.500);
            pr2.setDescription(pr2.getProductName() + " is the best");

            Product pr3 = new Product();
            pr3.setProductName("Dell XPS 15");
            pr3.setBrand(dell);
            pr3.setCategory(laptop);
            pr3.setPrice(150.500);
            pr3.setDescription(pr3.getProductName() + " is the best");

            Product pr4 = new Product();
            pr4.setProductName("MacBook Pro M3");
            pr4.setBrand(apple);
            pr4.setCategory(laptop);
            pr4.setPrice(150.500);
            pr4.setDescription(pr4.getProductName() + " is the best");

            Product pr5 = new Product();
            pr5.setProductName("Basic Cotton T-Shirt");
            pr5.setBrand(adidas);
            pr5.setCategory(clothes);
            pr5.setPrice(150.500);
            pr5.setDescription(pr5.getProductName() + " is the best");

            Product pr6 = new Product();
            pr6.setProductName("Nike Air Jordan High");
            pr6.setBrand(nike);
            pr6.setCategory(sneaker);
            pr6.setPrice(150.500);
            pr6.setDescription(pr6.getProductName() + " is the best");

            Product pr7 = new Product();
            pr7.setProductName("Clean Code");
            pr7.setBrand(prenticeHall);
            pr7.setCategory(books);
            pr7.setPrice(150.500);
            pr7.setDescription(pr7.getProductName() + " is the best");

            Product pr8 = new Product();
            pr8.setProductName("Harry Potter and the Sorcerer's Stone");
            pr8.setBrand(prenticeHall);
            pr8.setCategory(books);
            pr8.setPrice(250.500);
            pr8.setDescription(pr8.getProductName() + " is the best");

// Product 9: Đồng hồ thông minh
            Product pr9 = new Product();
            pr9.setProductName("Apple Watch Series 9");
            pr9.setBrand(apple);
            pr9.setCategory(smartWatch);
            pr9.setPrice(150.500);
            pr9.setDescription(pr9.getProductName() + " is the best");

            Product pr10 = new Product();
            pr10.setProductName("iPad Air 5");
            pr10.setBrand(apple);
            pr10.setCategory(tablet);
            pr10.setPrice(150.500);
            pr10.setDescription(pr10.getProductName() + " is the best");

            Product pr11 = new Product();
            pr11.setProductName("JBL Flip 6");
            pr11.setBrand(samsung);
            pr11.setCategory(furniture);
            pr11.setPrice(900.001);
            pr11.setDescription(pr11.getProductName() + " is the best");

        productRepository.saveAll(List.of(
                pr1, pr2, pr3, pr4, pr5, pr6, pr7, pr8, pr9, pr10, pr11
        ));


            productRepository.saveAll(List.of(pr1));
            log.info("Đã seed {} sản phẩm.", productRepository.count());

            log.info("Seeding data hoàn tất!");

    }
}
