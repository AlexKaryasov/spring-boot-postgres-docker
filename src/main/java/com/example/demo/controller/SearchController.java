package com.example.demo.controller;

import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class SearchController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public SearchController(
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository
    ) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/search-first")
    public Object searchFirst(@RequestParam(defaultValue = "") String q) {
        var usersFuture = CompletableFuture.supplyAsync(userRepository::findAll);
        var productFuture = CompletableFuture.supplyAsync(productRepository::findAll);
        var orderFuture = CompletableFuture.supplyAsync(orderRepository::findAll);

        Object result = CompletableFuture.anyOf(
                usersFuture,
                productFuture,
                orderFuture
        ).join();

        // Identify which result came first
        if (result.equals(usersFuture.join())) {
            return Map.of("source", "users", "data", result);
        } else if (result.equals(productFuture.join())) {
            return Map.of("source", "products", "data", result);
        } else {
            return Map.of("source", "orders", "data", result);
        }
    }
}
