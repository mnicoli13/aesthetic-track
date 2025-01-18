package IngDelSw.nicoli.controller;

import IngDelSw.nicoli.model.Product;
import IngDelSw.nicoli.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/store")
    public ResponseEntity<?> storeProduct(@RequestBody Product productRequest) {
        try {
            Product product = productService.storeProduct(
                    productRequest.getName()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("product", product);
            response.put("success", true);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        try {
            Product product = productService.getProduct(id);

            Map<String, Object> response = new HashMap<>();
            response.put("product", product);
            response.put("success", true);

            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product productRequest) {
        try {
            Product product = productService.updateProduct(
                    productRequest.getId(),
                    productRequest.getName()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("product", product);
            response.put("success", true);

            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);

            return ResponseEntity.ok("patient deleted successfully");  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @GetMapping("/getProducts")
    public ResponseEntity<?> getProducts() {
        try {
            List<Product> products = productService.getProducts();

            Map<String, Object> response = new HashMap<>();
            response.put("products", products);
            response.put("success", true);

            return ResponseEntity.ok(response);  //200 OK

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

}
