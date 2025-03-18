package IngDelSw.nicoli.services;

import IngDelSw.nicoli.model.Product;
import IngDelSw.nicoli.repository.ProductRepository;
import IngDelSw.nicoli.services.ProductService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(ProductService.class)
public class ProductServiceUnitTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private static Integer productId;

    @Test
    @DisplayName("Test 1: Save Product Test")
    @Order(1)
    @Rollback(value = false)
    public void saveProductTest() {
        Product createdProduct = productService.storeProduct("Test Product");
        productId = createdProduct.getId();

        System.out.println(createdProduct);
        Assertions.assertThat(createdProduct.getId()).isGreaterThan(0);
        Assertions.assertThat(createdProduct.getName()).isEqualTo("Test Product");
    }

    @Test
    @Order(2)
    public void getProductTest() {
        Product product = productService.getProduct(productId);

        System.out.println(product);
        Assertions.assertThat(product.getId()).isEqualTo(productId);
        Assertions.assertThat(product.getName()).isEqualTo("Test Product");
    }

    @Test
    @Order(3)
    public void getProductsTest() {
        List<Product> products = productService.getProducts();

        System.out.println(products);
        Assertions.assertThat(products.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateProductTest() {
        Product updatedProduct = productService.updateProduct(productId, "Updated Product");

        System.out.println(updatedProduct);
        Assertions.assertThat(updatedProduct.getName()).isEqualTo("Updated Product");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteProductTest() {
        productService.deleteProduct(productId);

        Assertions.assertThat(productRepository.findById(productId)).isEmpty();
    }
}
