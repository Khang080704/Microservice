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
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @Override
    public void run(String... args) throws Exception {
        categoryRepository.deleteAll();
        brandRepository.deleteAll();
        productRepository.deleteAll();
        colorRepository.deleteAll();
        sizeRepository.deleteAll();


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

            brandRepository.saveAll(List.of(apple,samsung,hp,dell, sony, lg, nike, adidas
            , gucci,ikea));
            log.info("Đã seed {} thương hiệu.", brandRepository.count());

            //Color seeding
            Color red = Color.builder().color("red").build();
            Color orange = Color.builder().color("orange").build();
            Color yellow = Color.builder().color("yellow").build();
            Color green = Color.builder().color("green").build();
            Color cyan = Color.builder().color("cyan").build();
            Color indigo = Color.builder().color("indigo").build();
            Color purple = Color.builder().color("purple").build();
            colorRepository.saveAll(List.of(red,orange,yellow,green,cyan, indigo,purple));

            // Size seeding
            Size s = Size.builder().size("small").build();
            Size m = Size.builder().size("medium").build();
            Size l = Size.builder().size("large").build();
            Size xl = Size.builder().size("xlarge").build();
            sizeRepository.saveAll(List.of(s, m, l, xl));


            // 3. Product seeding
            // Product 1
            Product pr1 = new Product();
            pr1.setProductName("Iphone 15 pro max");
            pr1.setBrand(apple);
            pr1.setCategory(smartPhone);
            pr1.setColor(List.of(
                    Color.builder().color("red").build(),
                    Color.builder().color("blue").build(),
                    Color.builder().color("green").build()
            ));


            productRepository.saveAll(List.of(pr1));
            log.info("Đã seed {} sản phẩm.", productRepository.count());

            log.info("Seeding data hoàn tất!");

    }
}
