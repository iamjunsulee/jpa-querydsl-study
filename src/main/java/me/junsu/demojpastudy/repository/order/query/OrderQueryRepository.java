package me.junsu.demojpastudy.repository.order.query;

import me.junsu.demojpastudy.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderQueryRepository extends JpaRepository<Order, Long> {
    @Query("select new me.junsu.demojpastudy.repository.order.query.OrderQueryDto(o.id, m.name, o.orderStatus, o.orderDate, d.address) from Order o join o.member m join o.delivery d")
    List<OrderQueryDto> findOrders();

    @Query("select new me.junsu.demojpastudy.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderQuantity, oi.orderPrice) from OrderItem oi join  oi.item i where oi.order.id = :id")
    List<OrderItemQueryDto> findOrderItems(@Param("id") Long id);

    @Query("select new me.junsu.demojpastudy.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderQuantity, oi.orderPrice) from OrderItem oi join  oi.item i where oi.order.id in :id")
    List<OrderItemQueryDto> findOrderItems2(@Param("id") List<Long> ids);
}
