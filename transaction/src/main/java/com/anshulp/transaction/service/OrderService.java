package com.anshulp.transaction.service;

import com.anshulp.transaction.dto.OrderResponse;
import com.anshulp.transaction.entity.Order;
import com.anshulp.transaction.entity.User;
import com.anshulp.transaction.exception.OrderNotFoundException;
import com.anshulp.transaction.mapper.Mapper;
import com.anshulp.transaction.repository.OrderRepository;
import com.anshulp.transaction.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Async
    @Transactional
    public CompletableFuture<OrderResponse> createOrder(String productName, double amount, Long id) throws InterruptedException {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        Order order = new Order();
        order.setUser(user);
        order.setProductName(productName);
        order.setAmount(amount);
        orderRepository.save(order);
        Thread.sleep(5000); // Simulate delay
        log.info("Current Thread name is : {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(Mapper.toDto(order));
    }

    @Async
    public CompletableFuture<List<OrderResponse>> getOrdersByUserId(Long userId) throws InterruptedException {
        log.info("Fetching orders for userId: {}", userId);
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> orderResponses = orders.stream().map(Mapper::toDto).toList();
        log.info("Fetched {} orders for userId: {}", orderResponses.size(), userId);
        Thread.sleep(1000); // Simulate delay
        return CompletableFuture.completedFuture(orderResponses);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order updateOrder(Long id, String productName, double amount) {
        Order order = getOrderById(id);
        order.setProductName(productName);
        order.setAmount(amount);
        return orderRepository.save(order);
    }

    public Page<Order> getPaginatedOrders(int page, int size, String sortBy, String sortDir) {
        String sortField = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "id";
        Sort sort = Sort.by(sortField);
        if (sortDir != null && sortDir.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return orderRepository.findAll(pageable);
    }
}
