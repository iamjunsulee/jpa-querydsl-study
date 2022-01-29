package me.junsu.demojpastudy.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//테이블과 매핑할 필요없이 공통으로 사용되는 매핑정보만 제공하기 위함
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {
    @CreatedDate
    public LocalDateTime createDate;

    @LastModifiedDate
    public LocalDateTime updateDate;
}
