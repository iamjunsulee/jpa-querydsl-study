package me.junsu.demojpastudy.domain;

import me.junsu.demojpastudy.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void auditing_test() {
        Address address = new Address("서울", "강남구", "01234");
        Member member = new Member("이준수", address);
        memberRepository.save(member);
        LocalDateTime createDate = member.getCreateDate();
        member.updateMemberInfo("삼준수", address);
        Member save = memberRepository.save(member);
        LocalDateTime updateDate = save.getUpdateDate();
        assertNotEquals(createDate, updateDate);
    }
}