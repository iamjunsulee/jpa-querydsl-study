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

@Repository
@RequiredArgsConstructor
public class OrderQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<OrderQueryDto> findOrders() {
        return jpaQueryFactory
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
                .fetch();
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
