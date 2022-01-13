package me.junsu.demojpastudy.repository;

import me.junsu.demojpastudy.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
    Page<Member> findByName(String name, Pageable pageable);
}
