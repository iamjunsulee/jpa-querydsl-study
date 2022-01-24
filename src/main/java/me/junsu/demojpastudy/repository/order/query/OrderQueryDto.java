package me.junsu.demojpastudy.repository.order.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.junsu.demojpastudy.domain.Address;
import me.junsu.demojpastudy.domain.OrderItem;
import me.junsu.demojpastudy.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemQueryDto> orderItems;
    private Address address;

    public OrderQueryDto(Long orderId, String name, OrderStatus orderStatus, LocalDateTime orderDate, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.address = address;
    }
}
