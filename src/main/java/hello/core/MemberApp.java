package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

/**
 * section 3. spring 으로 전환 내용 추가
 */
public class MemberApp {
    public static void main(String[] args) {
        //이렇게 test 하는 것은 좋지 않아요!
//        MemberService memberService = new MemberServiceImpl();
//        Member member = new Member(1L, "memberA", Grade.VIP);
//        memberService.join(member);
//
//
//        Optional<Member> findMember = memberService.findMember(member.getId());
//        System.out.println("member = " + member.getName());
//        findMember.ifPresent(m -> {
//            System.out.println("findMember = " + m.getName());
//        });

        //version 2 -> DIP 만족하는 상황에서 테스트 진행
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // * section 3. spring 으로 전환 내용 추가
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 위처럼 하면 스프링 컨테이너에 appConfig 안에 있는 빈들을 다 집어넣어서 관리해준다.

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);//기본적으로는 method 이름으로 빈에 등록된다.

        Member member = new Member(1L, "memberA", Grade.BASIC);
        memberService.join(member);

        Optional<Member> findMember = memberService.findMember(member.getId());
        System.out.println("member = " + member.getName());
        findMember.ifPresent(
                m -> {
                    System.out.println("member = " + m.getName());
                }
        );
    }
}
