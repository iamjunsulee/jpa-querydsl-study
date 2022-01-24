package me.junsu.demojpastudy.repository.order.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import static me.junsu.demojpastudy.domain.QOrder.order;
import static me.junsu.demojpastudy.domain.QDelivery.delivery;
import static me.junsu.demojpastudy.domain.QMember.member;
import static me.junsu.demojpastudy.domain.QOrderItem.orderItem;
import static me.junsu.demojpastudy.domain.QItem.item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<OrderQueryDto> findOrders() {
        List<Long> ids = jpaQueryFactory
                .select(order.id)
                .from(order)
                .innerJoin(order.member, member)
                .innerJoin(order.delivery, delivery)
                .fetch();

        List<OrderQueryDto> orders = jpaQueryFactory
                .select(Projections.fields(OrderQueryDto.class,
                        order.id.as("orderId")
                        , member.name
                        , order.orderDate
                        , order.orderStatus
                        , delivery.address
                ))
                .from(order)
                .innerJoin(order.member, member)
                .innerJoin(order.delivery, delivery)
                .where(order.id.in(ids))
                .fetch();

        List<OrderItemQueryDto> orderItems = this.findOrderItems(ids);
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
        orders.forEach(order -> order.setOrderItems(orderItemMap.get(order.getOrderId())));
        return orders;
    }

    public List<OrderItemQueryDto> findOrderItems(List<Long> ids) {
        return jpaQueryFactory
                .select(Projections.fields(OrderItemQueryDto.class,
                        order.id.as("orderId")
                        , item.name.as("itemName")
                        , orderItem.orderQuantity
                        , orderItem.orderPrice
                ))
                .from(orderItem)
                .innerJoin(orderItem.item, item)
                .innerJoin(orderItem.order, order)
                .where(orderItem.order.id.in(ids))
                .fetch();
    }
}
