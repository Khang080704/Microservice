package com.example.productservice.seed;

import com.example.productservice.model.Brand;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.model.ProductDetail;
import com.example.productservice.repository.BrandRepository;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
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
        categoryRepository.deleteAll();
        brandRepository.deleteAll();
        productRepository.deleteAll();

        if(productRepository.count()==0){

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

            // 3. Product seeding
            // Product 1
            Product pr1 = new Product();
            pr1.setProductName("Iphone 15 pro max");
            pr1.setBrand(apple);
            pr1.setCategory(smartPhone);
            pr1.setDetails(List.of(
                    ProductDetail.builder().quantity(20).color("red").price(10000.0).build(),
                    ProductDetail.builder().quantity(15).color("blue").price(15000.44).build(),
                    ProductDetail.builder().quantity(10).color("green").price(130.333).build()
            ));

            // 2. Samsung Smartphone
            Product pr2 = new Product();
            pr2.setProductName("Samsung Galaxy S24 Ultra");
            pr2.setBrand(samsung);
            pr2.setCategory(smartPhone);
            pr2.setDetails(List.of(
                    ProductDetail.builder().quantity(25).color("Titanium Gray").price(1299.99).build(),
                    ProductDetail.builder().quantity(20).color("Titanium Black").price(1299.99).build()
            ));

            // 3. Dell Laptop
            Product pr3 = new Product();
            pr3.setProductName("Dell XPS 15");
            pr3.setBrand(dell);
            pr3.setCategory(laptop);
            pr3.setDetails(List.of(
                    ProductDetail.builder().quantity(10).color("Silver").price(1899.00).build(),
                    ProductDetail.builder().quantity(5).color("Graphite").price(1999.00).build()
            ));

            // 4. HP Laptop
            Product pr4 = new Product();
            pr4.setProductName("HP Spectre x360 14");
            pr4.setBrand(hp);
            pr4.setCategory(laptop);
            pr4.setDetails(List.of(
                    ProductDetail.builder().quantity(12).color("Nightfall Black").price(1549.50).build()
            ));

            // 5. Apple Tablet
            Product pr5 = new Product();
            pr5.setProductName("iPad Air (M2)");
            pr5.setBrand(apple);
            pr5.setCategory(tablet);
            pr5.setDetails(List.of(
                    ProductDetail.builder().quantity(30).color("Space Gray").price(599.00).build(),
                    ProductDetail.builder().quantity(20).color("Starlight").price(599.00).build()
            ));

            // 6. Samsung Tablet
            Product pr6 = new Product();
            pr6.setProductName("Samsung Galaxy Tab S9");
            pr6.setBrand(samsung);
            pr6.setCategory(tablet);
            pr6.setDetails(List.of(
                    ProductDetail.builder().quantity(18).color("Beige").price(799.00).build()
            ));

            // 7. Sony Headphones
            Product pr7 = new Product();
            pr7.setProductName("Sony WH-1000XM5");
            pr7.setBrand(sony);
            pr7.setCategory(headphone);
            pr7.setDetails(List.of(
                    ProductDetail.builder().quantity(40).color("Black").price(399.99).build(),
                    ProductDetail.builder().quantity(30).color("Silver").price(399.99).build()
            ));

            // 8. Apple Watch
            Product pr8 = new Product();
            pr8.setProductName("Apple Watch Ultra 2");
            pr8.setBrand(apple);
            pr8.setCategory(smartWatch);
            pr8.setDetails(List.of(
                    ProductDetail.builder().quantity(15).color("Titanium").price(799.00).build()
            ));

            // 9. Dell Desktop
            Product pr9 = new Product();
            pr9.setProductName("Dell Alienware R16");
            pr9.setBrand(dell);
            pr9.setCategory(desktopPC);
            pr9.setDetails(List.of(
                    ProductDetail.builder().quantity(7).color("Dark Side of the Moon").price(2499.00).build()
            ));

            // 10. Sony Camera
            Product pr10 = new Product();
            pr10.setProductName("Sony Alpha a7 IV");
            pr10.setBrand(sony);
            pr10.setCategory(camera);
            pr10.setDetails(List.of(
                    ProductDetail.builder().quantity(9).color("Black").price(2498.00).build()
            ));

            // 11. LG TV
            Product pr11 = new Product();
            pr11.setProductName("LG OLED C4 65-inch");
            pr11.setBrand(lg);
            pr11.setCategory(tv);
            pr11.setDetails(List.of(
                    ProductDetail.builder().quantity(14).color("Black").price(2299.00).build()
            ));

            // 12. Samsung TV
            Product pr12 = new Product();
            pr12.setProductName("Samsung The Frame 75-inch");
            pr12.setBrand(samsung);
            pr12.setCategory(tv);
            pr12.setDetails(List.of(
                    ProductDetail.builder().quantity(10).color("Beige Frame").price(2999.00).build(),
                    ProductDetail.builder().quantity(8).color("White Frame").price(2999.00).build()
            ));

            // 13. LG Kitchen
            Product pr13 = new Product();
            pr13.setProductName("LG InstaView Refrigerator");
            pr13.setBrand(lg);
            pr13.setCategory(kitchenAppliances);
            pr13.setDetails(List.of(
                    ProductDetail.builder().quantity(5).color("Stainless Steel").price(3499.00).build()
            ));

            // 14. Samsung Kitchen
            Product pr14 = new Product();
            pr14.setProductName("Samsung Smart Microwave");
            pr14.setBrand(samsung);
            pr14.setCategory(kitchenAppliances);
            pr14.setDetails(List.of(
                    ProductDetail.builder().quantity(20).color("Black Stainless").price(349.00).build()
            ));

            // 15. Nike Sneaker
            Product pr15 = new Product();
            pr15.setProductName("Nike Air Force 1 '07");
            pr15.setBrand(nike);
            pr15.setCategory(sneaker);
            pr15.setDetails(List.of(
                    ProductDetail.builder().quantity(100).color("White").price(110.00).build(),
                    ProductDetail.builder().quantity(80).color("Black").price(110.00).build()
            ));

// 16. Adidas Sneaker
            Product pr16 = new Product();
            pr16.setProductName("Adidas Samba OG");
            pr16.setBrand(adidas);
            pr16.setCategory(sneaker);
            pr16.setDetails(List.of(
                    ProductDetail.builder().quantity(70).color("White/Black").price(100.00).build()
            ));

// 17. Nike T-Shirt
            Product pr17 = new Product();
            pr17.setProductName("Nike Sportswear Club Tee");
            pr17.setBrand(nike);
            pr17.setCategory(tShirt);
            pr17.setDetails(List.of(
                    ProductDetail.builder().quantity(50).color("Heather Gray").price(30.00).build(),
                    ProductDetail.builder().quantity(50).color("University Red").price(30.00).build()
            ));

// 18. Adidas Jacket
            Product pr18 = new Product();
            pr18.setProductName("Adidas Beckenbauer Track Jacket");
            pr18.setBrand(adidas);
            pr18.setCategory(jacket);
            pr18.setDetails(List.of(
                    ProductDetail.builder().quantity(30).color("Blue").price(80.00).build()
            ));

// 19. Gucci Bag
            Product pr19 = new Product();
            pr19.setProductName("Gucci Horsebit 1955 Bag");
            pr19.setBrand(gucci);
            pr19.setCategory(bag);
            pr19.setDetails(List.of(
                    ProductDetail.builder().quantity(5).color("GG Supreme").price(2980.00).build(),
                    ProductDetail.builder().quantity(3).color("Black Leather").price(3100.00).build()
            ));

// 20. Nike Jacket
            Product pr20 = new Product();
            pr20.setProductName("Nike Windrunner");
            pr20.setBrand(nike);
            pr20.setCategory(jacket);
            pr20.setDetails(List.of(
                    ProductDetail.builder().quantity(25).color("Red/White/Blue").price(100.00).build()
            ));

// 21. Gucci Jewelry
            Product pr21 = new Product();
            pr21.setProductName("Gucci Interlocking G Ring");
            pr21.setBrand(gucci);
            pr21.setCategory(jewelry);
            pr21.setDetails(List.of(
                    ProductDetail.builder().quantity(10).color("Silver").price(350.00).build()
            ));

// 22. Adidas Clothes
            Product pr22 = new Product();
            pr22.setProductName("Adidas Tiro 24 Pants");
            pr22.setBrand(adidas);
            pr22.setCategory(clothes);
            pr22.setDetails(List.of(
                    ProductDetail.builder().quantity(60).color("Black/White").price(50.00).build()
            ));

            // 23. IKEA Furniture
            Product pr23 = new Product();
            pr23.setProductName("IKEA BILLY Bookcase");
            pr23.setBrand(ikea);
            pr23.setCategory(furniture);
            pr23.setDetails(List.of(
                    ProductDetail.builder().quantity(40).color("White").price(59.99).build(),
                    ProductDetail.builder().quantity(20).color("Black-brown").price(59.99).build()
            ));

// 24. IKEA Furniture
            Product pr24 = new Product();
            pr24.setProductName("IKEA KALLAX Shelf unit");
            pr24.setBrand(ikea);
            pr24.setCategory(furniture);
            pr24.setDetails(List.of(
                    ProductDetail.builder().quantity(35).color("White").price(49.99).build()
            ));

// 25. IKEA Home Decor
            Product pr25 = new Product();
            pr25.setProductName("IKEA FEJKA Artificial Plant");
            pr25.setBrand(ikea);
            pr25.setCategory(homeDecor);
            pr25.setDetails(List.of(
                    ProductDetail.builder().quantity(100).color("Green").price(7.99).build()
            ));

// 26. Nike Sports Equipment
            Product pr26 = new Product();
            pr26.setProductName("Nike Premier League Strike Ball");
            pr26.setBrand(nike);
            pr26.setCategory(sportsEquipment);
            pr26.setDetails(List.of(
                    ProductDetail.builder().quantity(30).color("Yellow/Purple").price(35.00).build()
            ));

// 27. Adidas Sports Equipment
            Product pr27 = new Product();
            pr27.setProductName("Adidas Yoga Mat");
            pr27.setBrand(adidas);
            pr27.setCategory(sportsEquipment);
            pr27.setDetails(List.of(
                    ProductDetail.builder().quantity(45).color("Black").price(40.00).build()
            ));

// 28. Generic Book (Using a brand placeholder)
            Product pr28 = new Product();
            pr28.setProductName("The Great Gatsby");
            pr28.setBrand(gucci); // Giả sử Gucci phát hành sách (hoặc thay bằng brand phù hợp)
            pr28.setCategory(books);
            pr28.setDetails(List.of(
                    ProductDetail.builder().quantity(50).color("Paperback").price(15.99).build()
            ));

// 29. Generic Toy (Using a brand placeholder)
            Product pr29 = new Product();
            pr29.setProductName("Monopoly Classic");
            pr29.setBrand(ikea); // Giả sử IKEA bán đồ chơi (hoặc thay bằng brand phù hợp)
            pr29.setCategory(toys);
            pr29.setDetails(List.of(
                    ProductDetail.builder().quantity(25).color("Multi-color").price(24.99).build()
            ));

// 30. Apple Headphone (Airpods)
            Product pr30 = new Product();
            pr30.setProductName("Apple AirPods Pro 2");
            pr30.setBrand(apple);
            pr30.setCategory(headphone);
            pr30.setDetails(List.of(
                    ProductDetail.builder().quantity(60).color("White").price(249.00).build()
            ));

// 31. Sony PlayStation 5 (Example of using existing categories)
            Product pr31 = new Product();
            pr31.setProductName("Sony PlayStation 5");
            pr31.setBrand(sony);
            pr31.setCategory(desktopPC); // Dùng tạm "Desktop PC" làm danh mục cho console
            pr31.setDetails(List.of(
                    ProductDetail.builder().quantity(10).color("White").price(499.99).build()
            ));


            productRepository.saveAll(List.of(pr1,pr2,pr3,pr4, pr5, pr6, pr7, pr8, pr9, pr10
            , pr11, pr12, pr13, pr14, pr15, pr16, pr17, pr18, pr19, pr20,
            pr21, pr22, pr23, pr24, pr25, pr26, pr27, pr28, pr29, pr30
            , pr31));
            log.info("Đã seed {} sản phẩm.", productRepository.count());

            log.info("Seeding data hoàn tất!");
        }
        else {
            log.info("Đã có dữ liệu. Bỏ qua seeding.");
        }
    }
}
