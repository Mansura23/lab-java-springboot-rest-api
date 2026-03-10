package org.ironhack.rest_api.service;

import org.ironhack.rest_api.exception.LowerBoundException;
import org.ironhack.rest_api.exception.ProductNotFoundException;
import org.ironhack.rest_api.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final Map<String, Product> products = new HashMap<>();

    public ProductService() {
    }

    public List<Product> findAll() {
        return new ArrayList<Product>(products.values());

    }

    public Product save(String name, Double price, String category, int quantity) {
        Product product = new Product(name, price, category, quantity);
        products.put(name, product);
        return product;

    }

    public List<Product> findByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                productList.add(p);
            }
        }
        return productList;
    }

    public List<Product> findByPriceRange(double lower, double upper) {
        if (lower > upper) {
            throw new LowerBoundException("Lower bound is greater than upper bound");
        }
        return products.values()
                .stream()
                .filter(product -> product.getPrice() >= lower && product.getPrice() <= upper)
                .collect(Collectors.toList());

    }

    public Product findByName(String name) {
       if (products.containsKey(name)) {
           return products.get(name);
       }
       throw new ProductNotFoundException("Product not found");
    }

    public Product update(String name, double price, String category, int quantity) {
        Product oldProduct = findByName(name);
        oldProduct.setPrice(price);
        oldProduct.setCategory(category);
        oldProduct.setQuantity(quantity);
        products.put(name, oldProduct);
        return oldProduct;
    }

    public void delete(String name) {
        findByName(name);
        products.remove(name);
    }

}
