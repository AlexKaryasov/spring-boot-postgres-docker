package com.example.demo.controller;

import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repo;

    public OrderController(OrderRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<OrderEntity> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public OrderEntity create(@RequestBody OrderEntity order) {
        return repo.save(order);
    }
}
