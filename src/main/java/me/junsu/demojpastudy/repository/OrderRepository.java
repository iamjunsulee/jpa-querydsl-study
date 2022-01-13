package me.junsu.demojpastudy.repository;

import me.junsu.demojpastudy.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //fetch join 사용
    @Query("select o from Order o join fetch o.member m join fetch o.delivery d")
    List<Order> findAllWithMemberDelivery();

    @Query("select new me.junsu.demojpastudy.repository.SimpleOrderDto(o.id, m.name, o.orderStatus, o.orderDate, d.address) from Order o join o.member m join o.delivery d")
    List<SimpleOrderDto> findSimpleOrderDto();

    //distinct 키워드 사용
    //fetch join도 조인이기 때문에 1 대 다 인 경우, 다의 row 수 만큼 나오게 된다.
    //JPA의 distinct 를 사용하면, SQL 쿼리에 distinct 를 추가하고, 루트 객체가 중복일 경우, 중복을 제거한 후 컬렉션을 반환한다.
    //단, 1대다의 관계에서 fetch 조인을 사용할 경우, 페이징 처리가 불가능하다.
    @Query("select distinct o from Order o join fetch o.member m join fetch o.delivery d join fetch o.orderItems oi join fetch oi.item i")
    List<Order> findAllWithOrderItem();
}
