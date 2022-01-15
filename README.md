# JPA-Querydsl-Study Project
Vue.js + SpringBoot 기반의 프로젝트</br>
Spring Data Jpa에 Querydsl을 적용시켜보는 간단한 샘플 프로젝트

## install 및 실행
```
cd vue
npm install
npm run serve 
```

## Entity 정보
회원
```java
public class Member extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;            //회원번호
    private String name;        //회원명
    @Embedded
    private Address address;    //주소
}
```
상품
```java
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;            //상품번호
    private  String name;        //상품명
    private int stockQuantity;  //재고량
    private int price;          //가격
}
```
주문
```java
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
}
```
주문상품
```java
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
}
```
배송
```java
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
```