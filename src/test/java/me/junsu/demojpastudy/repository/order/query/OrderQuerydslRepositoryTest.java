package me.junsu.demojpastudy.repository.order.query;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderQuerydslRepositoryTest {
    @Autowired
    OrderQuerydslRepository orderQuerydslRepository;

    @Test
    void 조회() throws Exception {
        List<OrderQueryDto> orders = orderQuerydslRepository.findOrders();
        orders.forEach(System.out::println);
    }

    @Test
    void 상품조회() throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(33L);
        ids.add(37L);
        List<OrderItemQueryDto> orderItems = orderQuerydslRepository.findOrderItems(ids);
        orderItems.forEach(System.out::println);
    }
}