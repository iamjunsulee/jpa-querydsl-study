package me.junsu.demojpastudy.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book extends Item{
    private String author;

    @Builder
    public Book(String name, int stockQuantity, int price, String author) {
        this.setName(name);
        this.setStockQuantity(stockQuantity);
        this.setPrice(price);
        this.author = author;
    }
}
