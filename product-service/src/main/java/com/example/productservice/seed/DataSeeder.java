package com.example.productservice.seed;

import com.example.productservice.model.Brand;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repository.BrandRepository;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        if(productRepository.count()==0){
            categoryRepository.deleteAll();
            brandRepository.deleteAll();

            // 1. Category seeding
            Category smartPhone = Category.builder().categoryName("Smart Phone").build();
            Category laptop = Category.builder().categoryName("Laptop").build();
            Category sneaker = Category.builder().categoryName("Sneaker").build();
            Category bag = Category.builder().categoryName("Bag").build();
            Category clothes = Category.builder().categoryName("Clothes").build();

            categoryRepository.saveAll(List.of(smartPhone,laptop, bag, sneaker,clothes));
            log.info("Đã seed {} danh mục.", categoryRepository.count());

            // 2. Brand seeding
            Brand apple = Brand.builder().name("Apple").build();
            Brand samsung = Brand.builder().name("Samsung").build();
            Brand hp = Brand.builder().name("HP").build();
            Brand dell = Brand.builder().name("Dell").build();

            brandRepository.saveAll(List.of(apple,samsung,hp,dell));
            log.info("Đã seed {} thương hiệu.", brandRepository.count());

            // 3. Product seeding
            // Product 1
            Product pr1 = new Product();
            pr1.setPrice(300000.0);
            pr1.setProductName("Iphone 15 pro max");
            pr1.setBrand(apple);
            pr1.setCategory(smartPhone);

            // Product 2
            Product pr2 = new Product();
            pr2.setPrice(1500000.0);
            pr2.setProductName("Galaxy S24 Ultra 512GB");
            pr2.setBrand(samsung);
            pr2.setCategory(smartPhone);

            // Product 3
            Product pr3 = new Product();
            pr3.setPrice(1234455.44);
            pr1.setProductName("HP elite 850 g5");
            pr1.setBrand(hp);
            pr1.setCategory(laptop);

            // Product 4
            Product pr4 = new Product();
            pr1.setPrice(60000000.0);
            pr1.setProductName("HP bag");
            pr1.setBrand(hp);
            pr1.setCategory(bag);

            productRepository.saveAll(List.of(pr1,pr2,pr3,pr4));
            log.info("Đã seed {} sản phẩm.", productRepository.count());

            log.info("Seeding data hoàn tất!");
        }
        else {
            log.info("Đã có dữ liệu. Bỏ qua seeding.");
        }
    }
}
