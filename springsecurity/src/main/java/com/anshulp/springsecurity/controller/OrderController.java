package com.anshulp.springsecurity.controller;

import com.anshulp.springsecurity.dto.OrderRequest;
import com.anshulp.springsecurity.dto.OrderResponse;
import com.anshulp.springsecurity.entity.User;
import com.anshulp.springsecurity.security.CustomUserDetails;
import com.anshulp.springsecurity.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest,
                                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        OrderResponse orderResponse = orderService.createOrder(orderRequest, user);
        return ResponseEntity.ok(orderResponse);
    }
}
