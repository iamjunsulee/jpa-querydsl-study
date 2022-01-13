package me.junsu.demojpastudy.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.junsu.demojpastudy.domain.Address;
import me.junsu.demojpastudy.domain.Member;
import me.junsu.demojpastudy.repository.member.query.MemberQueryRepository;
import me.junsu.demojpastudy.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    private final MemberQueryRepository memberQueryRepository;

    @PostMapping("/api/v1/members")
    public MemberResponse saveMemberVer1(@RequestBody Member member) {
        Long savedId = memberService.save(member);
        return new MemberResponse(savedId);
        //api 요청 스펙에 맞춰서 별도의 DTO를 작성하는 것이 좋다.
        //Entity를 외부에 노출해선 안된다.
    }

    @PostMapping("/api/v2/members")
    public MemberResponse saveMemberVer2(@RequestBody MemberRequest request) {
        System.out.println(request.getAddress());
        Member member = new Member(request.getName(), request.getAddress());
        Long savedId = memberService.save(member);
        return new MemberResponse(savedId);
    }

    @GetMapping("/api/v1/members")
    public List<Member> getMembers() {
        return memberService.findAll();
        //엔티티의 모든 값이 노출된다.
        // -> 노출하기 싫은 컬럼에 대해 @JsonIgnore 하면 노출 안시킬 수 있으나 엔티티에 프레젠테이션 로직이 들어가버린다.
        //용도에 따라 API가 다양하게 만들어질텐데 한 엔티티에 모든 API를 위한 프레젠테이션 응답 로직을 담기는 어렵다.
        //엔티티가 변경되면 API 스펙이 변해버린다.
    }

    @GetMapping("/api/v2/members")
    public Result<List<MemberDto>> getMembersV2(@RequestParam(required = false) String name) {
//        List<Member> memberList = new ArrayList<>();
//        if (name == null || name.trim().isEmpty()) {
//            memberList = memberService.findAll();
//        }
//        else {
//            memberList = memberService.findByName(name);
//        }
        List<Member> memberList = memberQueryRepository.findMembersByName(name);
        List<MemberDto> result = memberList.stream()
                .map(m -> new MemberDto(m.getId(), m.getName(), m.getAddress())).collect(Collectors.toList());
        return new Result<>(result.size(), result);
        //별도의 Result 클래스를 생성해서 사용하면 API 스펙 변경에 유연하다.
    }

    @GetMapping("/api/members")
    public PageResult<List<MemberDto>> getAllMembers(@RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page) {
        Page<Member> memberPage;
        Pageable paging = PageRequest.of(page, 5);

        if (name == null || name.trim().isEmpty()) {
            memberPage = memberService.getMemberListWithPage(paging);
        }
        else {
            memberPage = memberService.getMemberByNameWithPage(name, paging);
        }

        List<Member> memberList = memberPage.getContent();
        List<MemberDto> memberDto = memberList.stream()
                .map(m -> new MemberDto(m.getId(), m.getName(), m.getAddress())).collect(Collectors.toList());

        return new PageResult<>(memberDto, memberPage.getNumber(), memberPage.getTotalPages(), memberPage.getTotalElements());
    }
//    @GetMapping("/api/members/{name}")
//    public Result<List<MemberDto>> getMemberByName(@PathVariable String name) {
//        List<Member> memberList = memberService.findByName(name);
//        List<MemberDto> collect = memberList.stream().map(m -> new MemberDto(m.getId(), m.getName(), m.getAddress())).collect(Collectors.toList());
//        return new Result<>(1, collect);
//    }

    @DeleteMapping("/api/members/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteById(id);
    }

    @GetMapping("/api/members/{id}")
    public Result<MemberDto> getMemberById(@PathVariable Long id) {
        Member member = memberService.findById(id);
        MemberDto memberDto = new MemberDto(member.getId(), member.getName(), member.getAddress());
        return new Result<>(1, memberDto);
    }

    @PutMapping("/api/members/{id}")
    public MemberResponse updateMember(@PathVariable Long id, @RequestBody MemberRequest request) {
        memberService.updateMember(id, request);
        return new MemberResponse(id);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class PageResult<T> {
        private T members;
        private int currentPage;
        private int totalPages;
        private long totalMembers;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private Long id;
        private String name;
        private Address address;
    }

    @Data
    @AllArgsConstructor
    static class MemberResponse {
        private Long id;
    }

    @Data
    public static class MemberRequest {
        private String name;
        private Address address;
    }
}
