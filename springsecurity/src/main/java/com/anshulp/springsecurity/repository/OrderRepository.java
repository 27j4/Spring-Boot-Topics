package com.anshulp.springsecurity.repository;

import com.anshulp.springsecurity.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
