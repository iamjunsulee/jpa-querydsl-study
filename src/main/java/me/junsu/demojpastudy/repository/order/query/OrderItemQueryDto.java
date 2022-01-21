package me.junsu.demojpastudy.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemQueryDto {
    @JsonIgnore
    private Long orderId;
    private String itemName;
    private int orderQuantity;  //주문수량
    private int orderPrice;     //주문가격

    public OrderItemQueryDto(Long orderId, String itemName, int orderQuantity, int orderPrice) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
    }
}
