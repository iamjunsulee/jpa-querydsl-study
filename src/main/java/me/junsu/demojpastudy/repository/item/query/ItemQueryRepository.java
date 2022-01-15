package me.junsu.demojpastudy.repository.item.query;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.junsu.demojpastudy.domain.Item;
import static me.junsu.demojpastudy.domain.QItem.item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Item> findItems(Pageable pageable) {
        QueryResults<Item> itemQueryResults = jpaQueryFactory
                .selectFrom(item)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Item> content = itemQueryResults.getResults();
        return new PageImpl<>(content, pageable, itemQueryResults.getTotal());
    }
}
