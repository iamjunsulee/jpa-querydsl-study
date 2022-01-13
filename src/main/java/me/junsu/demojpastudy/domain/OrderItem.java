package me.junsu.demojpastudy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    private int orderQuantity;  //주문수량
    private int orderPrice;     //주문가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;          //상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;        //주문

    /* 생성메서드 */
    public static OrderItem createOrderItem(Item item, int price, int orderQuantity) throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(price);
        orderItem.setOrderQuantity(orderQuantity);
        item.removeStock(orderQuantity);
        return orderItem;
    }

    /* 비지니스 메소드 */
    //주문취소
    public void cancel() {
        getItem().addStock(orderQuantity);
    }

    //가격조회
    public int getTotalPrice() {
        return getOrderPrice() * orderQuantity;
    }
}
