package me.junsu.demojpastudy.repository.member.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.junsu.demojpastudy.domain.Member;
import org.springframework.stereotype.Repository;
import static me.junsu.demojpastudy.domain.QMember.member;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Member> findMembersByName(String name) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!Objects.isNull(name)) {
            builder.and(member.name.eq(name));
        }

        return jpaQueryFactory.selectFrom(member)
                .where(builder)
                .fetch();
    }
}
