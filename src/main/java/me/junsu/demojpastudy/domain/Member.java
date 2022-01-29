package me.junsu.demojpastudy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;            //회원번호

    private String name;        //회원명

    @Embedded
    private Address address;    //주소

    @Builder
    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    //연관관계의 주인이 아님을 명시
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    //변경 메소드
    public void updateMemberInfo(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
