package me.junsu.demojpastudy.repository.member.query;

import me.junsu.demojpastudy.config.QueryDslConfig;
import me.junsu.demojpastudy.domain.Address;
import me.junsu.demojpastudy.domain.Member;
import me.junsu.demojpastudy.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberQueryRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Test
    void 기존_페이징_방식() throws Exception {
        Pageable firstPage = PageRequest.of(1, 10);

        for (int i = 1;i <= 50;i++) {
            memberRepository.save(Member.builder()
            .name(i + "junsu")
            .address(new Address("seoul", "mapo", "01234"))
            .build());
        }

        List<Member> members = memberQueryRepository.findMembersByName(null, firstPage).getContent();

        assertEquals(members.size(), 10);
        assertEquals(members.get(0).getName(), "1junsu");
        assertEquals(members.get(9).getName(), "10junsu");
    }

    @Test
    void no_off_set_방식_첫페이지() throws Exception {
        for (int i = 1;i <= 50;i++) {
            memberRepository.save(Member.builder()
                    .name(i + "junsu")
                    .address(new Address("seoul", "mapo", "01234"))
                    .build());
        }
        List<Member> members = memberQueryRepository.noOffSet(null, null, 10);

        assertEquals(members.get(0).getName(), "50junsu");
        assertEquals(members.get(9).getName(), "41junsu");
    }

    @Test
    void no_off_set_방식_두번째페이지() throws Exception {
        for (int i = 1;i <= 50;i++) {
            memberRepository.save(Member.builder()
                    .name(i + "junsu")
                    .address(new Address("seoul", "mapo", "01234"))
                    .build());
        }
        List<Member> members = memberQueryRepository.noOffSet(41L, null, 10);

        assertEquals(members.get(0).getName(), "40junsu");
        assertEquals(members.get(9).getName(), "31junsu");
    }
}