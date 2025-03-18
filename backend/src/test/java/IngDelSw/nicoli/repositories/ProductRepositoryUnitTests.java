package IngDelSw.nicoli.repositories;

import IngDelSw.nicoli.model.Product;
import IngDelSw.nicoli.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryUnitTests {

    @Autowired
    private ProductRepository productRepository;
    private static Integer productId;

    @Test
    @DisplayName("Test 1: Save Product Test")
    @Order(1)
    @Rollback(value = false)
    public void saveProductTest() {

        Product product = new Product();
        product.setName("Product A");

        Product createdProduct = productRepository.save(product);
        productId = createdProduct.getId();

        System.out.println(createdProduct);
        Assertions.assertThat(createdProduct.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getProductTest() {

        Product product = productRepository.findById(productId).get();

        System.out.println(product);
        Assertions.assertThat(product.getId()).isEqualTo(productId);
    }

    @Test
    @Order(3)
    public void getAllProductsTest() {

        List<Product> products = productRepository.findAll();

        System.out.println(products);
        Assertions.assertThat(products.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void getProductNotFoundTest() {

        Optional<Product> product = productRepository.findById(999);  // Assumendo che 999 non esista

        Assertions.assertThat(product).isEmpty();
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void updateProductTest() {

        Product product = productRepository.findById(productId).get();
        product.setName("Updated description for Product A");
        Product updatedProduct = productRepository.save(product);

        System.out.println(updatedProduct);
        Assertions.assertThat(updatedProduct.getName()).isEqualTo("Updated description for Product A");
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void deleteProductTest() {

        productRepository.deleteById((long) productId);

        Assertions.assertThat(productRepository.findById(productId)).isEmpty();
    }
}
