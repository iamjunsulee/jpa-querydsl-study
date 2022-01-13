package me.junsu.demojpastudy.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//테이블과 매핑할 필요없이 공통으로 사용되는 매핑정보만 제공하기 위함
@MappedSuperclass
public abstract class BaseEntity {
    public LocalDateTime createDate;
}
