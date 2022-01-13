package me.junsu.demojpastudy.repository;

import lombok.Data;
import me.junsu.demojpastudy.domain.Address;
import me.junsu.demojpastudy.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class SimpleOrderDto {
    private Long orderId;
    private String name;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private Address deliveryAddress;

    public SimpleOrderDto(Long orderId, String name, OrderStatus orderStatus, LocalDateTime orderDate, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.deliveryAddress = address;
    }
}
