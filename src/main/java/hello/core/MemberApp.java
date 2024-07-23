package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

import java.util.Optional;

public class MemberApp {
    public static void main(String[] args) {
        //이렇게 test 하는 것은 좋지 않아요!
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);


        Optional<Member> findMember = memberService.findMember(member.getId());
        System.out.println("member = " + member.getName());
        findMember.ifPresent(m -> {
            System.out.println("findMember = " + m.getName());
        });
    }
}
