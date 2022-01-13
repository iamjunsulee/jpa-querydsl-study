package me.junsu.demojpastudy.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;                    //주문번호
    private LocalDateTime orderDate;    //주문날짜

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    //주문상태

    //연관관계의 주인은 외래 키가 있는 Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;              //주문회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    //@BatchSize(size = 10)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;          //배송

    /* 생성 메서드 */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems) {
            order.setOrderItem(orderItem);
        }

        return order;
    }

    /*  연관관계 편의 메서드 */
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getOrders().remove(this);   //기존 팀과의 관계를 제거
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    /* 비지니스 로직 */
    //주문취소
    public void cancel() {
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMPLETE) {
            throw new RuntimeException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setOrderStatus(OrderStatus.CANCELED);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //주문가격조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
