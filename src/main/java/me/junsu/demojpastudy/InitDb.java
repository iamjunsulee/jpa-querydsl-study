package me.junsu.demojpastudy;

import lombok.RequiredArgsConstructor;
import me.junsu.demojpastudy.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() throws Exception {
        initService.initDb();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void initDb() throws Exception {
            Book item1 = new Book("Spring Book", 100, 10000, "lee");
            Book item2 = new Book("Summer Book", 100,20000, "lee");
            Book item3 = new Book("Fall Book", 50, 25000, "lee");
            Book item4 = new Book("Winter Book", 50, 30000, "lee");
            Book item5 = new Book("Spring Data JPA", 11, 35000, "lee");
            Book item6 = new Book("SpringFramework 5.0", 29, 30000, "lee");
            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(item4);
            em.persist(item5);
            em.persist(item6);

            Address address1 = new Address("서울특별시", "마포구 마포대로", "04413");
            Address address2 = new Address("부산광역시", "금정구", "01111");
            Address address3 = new Address("서울특별시", "마포구", "01112");
            Address address4 = new Address("서울특별시", "강서구", "01113");
            Address address5 = new Address("서울특별시", "강남구", "01114");
            Address address6 = new Address("서울특별시", "종로구", "01115");
            Address address7 = new Address("서울특별시", "서대문구", "01116");
            Address address8 = new Address("서울특별시", "성북구", "01117");
            Address address9 = new Address("서울특별시", "성동구", "01118");
            Address address10 = new Address("서울특별시", "강북구", "01119");
            //회원 생성
            Member member1 = new Member("이준수", address1);
            Member member2 = new Member("삼준수", address2);
            Member member3 = new Member("사준수", address3);
            Member member4 = new Member("오준수", address4);
            Member member5 = new Member("육준수", address5);
            Member member6 = new Member("칠준수", address6);
            Member member7 = new Member("팔준수", address7);
            Member member8 = new Member("구준수", address8);
            Member member9 = new Member("십준수", address9);
            Member member10 = new Member("십일준수", address10);
            Member member11 = new Member("십이준수", address10);
            Member member12 = new Member("십삼준수", address10);
            Member member13 = new Member("십사준수", address10);
            Member member14 = new Member("십오준수", address10);
            Member member15 = new Member("십육준수", address10);
            Member member16 = new Member("십칠준수", address10);
            Member member17 = new Member("십팔준수", address10);
            Member member18 = new Member("십구준수", address10);
            Member member19 = new Member("이십준수", address10);
            Member member20 = new Member("이십일준수", address10);
            Member member21 = new Member("이십이준수", address10);
            Member member22 = new Member("이십삼준수", address10);
            Member member23 = new Member("이십사준수", address10);
            Member member24 = new Member("이십오준수", address10);
            Member member25 = new Member("이십육준수", address10);
            Member member26 = new Member("이십칠준수", address10);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);
            em.persist(member6);
            em.persist(member7);
            em.persist(member8);
            em.persist(member9);
            em.persist(member10);
            em.persist(member11);
            em.persist(member12);
            em.persist(member13);
            em.persist(member14);
            em.persist(member15);
            em.persist(member16);
            em.persist(member17);
            em.persist(member18);
            em.persist(member19);
            em.persist(member20);
            em.persist(member21);
            em.persist(member22);
            em.persist(member23);
            em.persist(member24);
            em.persist(member25);
            em.persist(member26);

            //주문상품 생성
            OrderItem orderItem1 = OrderItem.createOrderItem(item1, 10000,1);
            OrderItem orderItem2 = OrderItem.createOrderItem(item2, 20000,2);
            OrderItem orderItem3 = OrderItem.createOrderItem(item3, 25000,3);
            OrderItem orderItem4 = OrderItem.createOrderItem(item4, 30000,4);

            //배송생성
            Delivery delivery = new Delivery(address1);
            Delivery delivery2 = new Delivery(address2);

            //주문생성
            Order order = Order.createOrder(member1, delivery, orderItem4, orderItem2);
            em.persist(order);

            Order order2 = Order.createOrder(member2, delivery2, orderItem1, orderItem3);
            em.persist(order2);
        }
    }
}
