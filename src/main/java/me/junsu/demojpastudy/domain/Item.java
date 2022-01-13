package me.junsu.demojpastudy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;            //상품번호
    private  String name;        //상품명
    private int stockQuantity;  //재고량
    private int price;          //가격

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) throws Exception {
        int rest = stockQuantity - quantity;
        if (rest < 0) {
            throw new Exception("재고 없음");
        }
        this.stockQuantity = rest;
    }

    //변경메소드
    public void updateItemInfo(String name, int stockQuantity, int price) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }
}
