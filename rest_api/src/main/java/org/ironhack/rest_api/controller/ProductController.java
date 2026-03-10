package org.ironhack.rest_api.controller;

import jakarta.validation.Valid;
import org.ironhack.rest_api.exception.MissingKeyHeaderException;
import org.ironhack.rest_api.model.Customer;
import org.ironhack.rest_api.model.Product;
import org.ironhack.rest_api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
    private final static String API_KEY = "123456789";

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void checkApiKey(String apiKey) {
        if (apiKey == null) {
            throw new MissingKeyHeaderException("Missing API Key");
        }
        if (!apiKey.matches(API_KEY)) {
            throw new MissingKeyHeaderException("Invalid API Key");
        }
    }

    @GetMapping
    public List<Product> getProducts(@RequestHeader String apiKey) {
        checkApiKey(apiKey);
        return productService.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestHeader String apiKey, @Valid @RequestBody Product product) {
        checkApiKey(apiKey);
        Product created = productService.save(product.getName(), product.getPrice(), product.getCategory(), product.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@RequestHeader String apiKey, @PathVariable String category) {
        checkApiKey(apiKey);
        return productService.findByCategory(category);
    }

    @GetMapping("/{name}")
    public Product getProductsByName(@RequestHeader String apiKey, @PathVariable String name) {
        checkApiKey(apiKey);
        return productService.findByName(name);
    }

    @GetMapping("/price")
    public List<Product> getProductsByPriceRange(@RequestHeader String apiKey,
                                                 @RequestParam(required = false) double lower,
                                                 @RequestParam(required = false) double higher) {
        checkApiKey(apiKey);
        return productService.findByPriceRange(lower, higher);
    }
    @PutMapping("/{name}")
    public ResponseEntity<Product> updateProduct(@RequestHeader String apiKey, @PathVariable String name, @Valid @RequestBody Product product) {
        checkApiKey(apiKey);
        Product product1 = productService.update(name,product.getPrice(),product.getCategory(),product.getQuantity());
        return ResponseEntity.ok(product1);
    }

    @DeleteMapping("/{name}")
    public void deleteProduct(@RequestHeader String apiKey, @PathVariable String name) {
        checkApiKey(apiKey);
        productService.delete(name);

    }


}
