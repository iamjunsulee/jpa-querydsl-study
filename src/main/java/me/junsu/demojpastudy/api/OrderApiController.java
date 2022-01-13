package me.junsu.demojpastudy.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.junsu.demojpastudy.domain.Address;
import me.junsu.demojpastudy.domain.Order;
import me.junsu.demojpastudy.domain.OrderItem;
import me.junsu.demojpastudy.domain.OrderStatus;
import me.junsu.demojpastudy.repository.SimpleOrderDto;
import me.junsu.demojpastudy.repository.order.query.OrderQueryDto;
import me.junsu.demojpastudy.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> getOrders() {
        return orderService.findAll();
        //무한루프에 빠지게 된다.
        //Order를 조회하면서 Member를 찾게 되고, Member 안에서 또 Order를 만나고 이렇게 계속 무한 루프
        //양방향 연관관계 때문에 발생한다.
        //해결법 1. @JsonIgnore
        //지연로딩으로 인해 오류 발생. 지연 로딩은 프록시 객체를 생성하는데, json이 이 프록시 객체를 인식하지 못해서 문제가 생김.
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> getOrdersVer2() {
        //엔티티를 DTO로 변환하는 방법
        //N + 1 문제에 빠진다.
        
        //Order 조회(결과 N건)
        List<Order> orders = orderService.findAll();
        
        //Order -> Member : 지연 로딩 조회 N번
        //Order -> delivery : 지연 로딩 조회 N번
        //Order 조회 결과가 2건이라하면 최악의 경우, 1 + 2(회원) + 2(배송) 총 5번의 쿼리가 실행됨.
        //최악의 경우라고 하는 이유는 지연로딩의 경우, 영속성 컨텍스트에서 조회하므로 이미 조회해서 영속성 컨텍스트가 가지고 있는 경우, 쿼리를 생략하게 된다.
        return orders.stream().map(o -> new SimpleOrderDto(o.getId(), o.getMember().getName(), o.getOrderStatus(), o.getOrderDate(), o.getDelivery().getAddress())).collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> getOrdersVer3() {
        //fetch join을 사용하는 방법
        //쿼리 한 번에 다 조회하므로 지연로딩 신경 쓸 필요 없음.
        List<Order> orders = orderService.findAllWithMemberDelivery();
        return orders.stream().map(o -> new SimpleOrderDto(o.getId(), o.getMember().getName(), o.getOrderStatus(), o.getOrderDate(), o.getDelivery().getAddress())).collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderDto> getOrdersVer4() {
        //리포지토리에서 바로 DTO를 조회하는 방법
        //new 명령어를 통해서 JPQL의 결과를 바로 DTO로 변환
        //select 절에서 필요한 데이터만 조회함. 생각보다 성능효과는 미비
        //리포지토리에서 재사용성이 떨어진다.
        // Api 스펙에 맞춘 코드가 리포지토리에 들어가게 된다. -> 리포지토리는 엔티티에 대한 객체 그래프 탐색에 이용되야 되는데 Api 스펙에 맞춰 사용이 됨
        return orderService.findSimpleOrderDto();
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> getOrdersWithCollectionVer2() {
        List<Order> orders = orderService.findAll();
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> getOrdersWithCollectionVer3() {
        List<Order> orders = orderService.findAllWithOrderItem();
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @GetMapping("/api/v3_1/orders")
    public List<OrderDto> getOrdersWithCollectionVer3_1() {
        //컬렉션의 경우, fetch 조인을 사용하면 페이징을 쓸수가 없다.
        //xToOme 관계에서 fetch 조인해도 페이징 결과에는 영향을 주지 않는다.
        //따라서XtoOne 관계는 fetch 조인으로 쿼리 수를 줄이고, 컬렉션 타입은 지연로딩을 사용한다.
        //단 이 방법에서 성능을 최적화하려면 spring.jpa.properties.hibernate.default_batch_fetch_size 설정을 하거나
        //개별적으로 설정하려면 @BetchSize 어노테이션을 사용한다.
        List<Order> orders = orderService.findAllWithMemberDelivery();
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> getOrdersWithCollectionVer4() {
        return orderService.findOrderQueryDto();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> getOrdersWithCollectionVer5() {
        return orderService.findOrderQueryDto2();
    }

    @PostMapping("/api/orders")
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request) throws Exception {
        Long saveOrderId = orderService.createOrder(request);
        return new CreateOrderResponse(saveOrderId);
    }

    @PostMapping("/api/orders/{id}")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private OrderStatus orderStatus;
        private LocalDateTime orderDate;
        private Address address;
        private List<OrderItemDto> orderItems;
        //private List<OrderItem> orderItems;  엔티티를 노출하면 안됨.

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderStatus = order.getOrderStatus();
            orderDate = order.getOrderDate();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int orderQuantity;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            orderQuantity = orderItem.getOrderQuantity();
        }
    }

    @Data
    public static class CreateOrderRequest {
        private Long memberId;
        private Long itemId;
        private int orderPrice;
        private int orderQuantity;
        private Address address;
    }

    @Data
    @AllArgsConstructor
    static class CreateOrderResponse {
        private Long id;
    }
}
