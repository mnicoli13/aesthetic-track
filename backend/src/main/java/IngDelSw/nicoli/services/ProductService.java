package IngDelSw.nicoli.services;

import IngDelSw.nicoli.model.Product;
import IngDelSw.nicoli.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product storeProduct(String productName) {

        // Crea un nuovo trattamento
        Product product = new Product();
        product.setName(productName);

        return productRepository.save(product);
    }

    public Product getProduct(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found with id " + id));
    }

    public Product updateProduct(Integer id, String productName) {
        // Verifica se il paziente esiste
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Aggiorna i campi desiderati
        product.setName(productName != null ? productName : product.getName());

        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        productRepository.delete(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

}

