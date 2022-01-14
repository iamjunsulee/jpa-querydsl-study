package me.junsu.demojpastudy.repository.member.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.junsu.demojpastudy.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import static me.junsu.demojpastudy.domain.QMember.member;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Member> findMembersByName(String name) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(eqName(name))
                .fetch();
    }

    public Page<Member> findMembersByName(String name, Pageable pageable) {
        QueryResults<Member> queryResults = jpaQueryFactory
                .selectFrom(member)
                .where(eqName(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        //fetchResults() 를 사용하니까 Member를 조회하는 쿼리, total count를 조회하는 쿼리 총 2번 실행된다.
        List<Member> content = queryResults.getResults();
        return new PageImpl<>(content, pageable, queryResults.getTotal());
    }

    private BooleanExpression eqName(String name) {
        if  (Objects.isNull(name)) {
            return null;
        }
        return member.name.eq(name);
    }
}
