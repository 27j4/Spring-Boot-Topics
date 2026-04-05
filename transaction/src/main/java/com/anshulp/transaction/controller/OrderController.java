package com.anshulp.transaction.controller;

import com.anshulp.transaction.dto.OrderResponse;
import com.anshulp.transaction.entity.Order;
import com.anshulp.transaction.mapper.Mapper;
import com.anshulp.transaction.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/createOrder/{id}")
    public CompletableFuture<ResponseEntity<OrderResponse>> createOrder(@RequestParam String productName, @RequestParam double amount, @PathVariable Long id) throws InterruptedException {
        return orderService.createOrder(productName, amount, id)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex ->
                        ResponseEntity.badRequest().build()
                );
    }

    @GetMapping("/getPaginatedOrders")
    public Page<Order> getPaginatedOrders(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortDir) {
        return orderService.getPaginatedOrders(page, size, sortBy, sortDir);
    }

    @GetMapping("/getByUserId/{userId}")
    public CompletableFuture<ResponseEntity<List<OrderResponse>>> getOrdersByUserId(
            @PathVariable Long userId) throws InterruptedException {

        return orderService.getOrdersByUserId(userId)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex ->
                        ResponseEntity.badRequest().build()
                );
    }

    @GetMapping("/orders/{id}")
    public String getOrderById(@PathVariable Long id) {
        orderService.getOrderById(id);
        return "Order retrieved successfully";
    }

    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order deleted successfully";
    }

    @PutMapping("/orders/{id}")
    public OrderResponse updateOrder(@PathVariable Long id, @RequestParam String productName, @RequestParam double amount) {
        return Mapper.toDto(orderService.updateOrder(id, productName, amount));
    }
}
