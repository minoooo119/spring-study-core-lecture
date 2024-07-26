package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {
    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean( MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
        /**
         * 아래와 같이 스캔해서 빈으로 등록을 해준다.
         * 11:00:21.939 [main] DEBUG o.s.c.a.ClassPathBeanDefinitionScanner --
         *          Identified candidate component class: file [/Users/mino/Desktop/mino/core/out/production/classes/hello/core/discount/RateDiscountPolicy.class]
         * 11:00:21.940 [main] DEBUG o.s.c.a.ClassPathBeanDefinitionScanner --
         *          Identified candidate component class: file [/Users/mino/Desktop/mino/core/out/production/classes/hello/core/member/MemberServiceImpl.class]
         * 11:00:21.941 [main] DEBUG o.s.c.a.ClassPathBeanDefinitionScanner --
         *          Identified candidate component class: file [/Users/mino/Desktop/mino/core/out/production/classes/hello/core/member/MemoryMemberRepository.class]
         * 11:00:21.941 [main] DEBUG o.s.c.a.ClassPathBeanDefinitionScanner --
         *          Identified candidate component class: file [/Users/mino/Desktop/mino/core/out/production/classes/hello/core/order/OrderServiceImpl.class]
         */
    }

    @Test
    void 등록된_빈_versus_그냥_불러온() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);


        MemberServiceImpl memberServiceTestA = new MemberServiceImpl(new MemoryMemberRepository());
        //의존 관계 주입 및 그런 객체들은 등록된 객체를 사용한다.
        // MemoryMemberRepository 를 사용하는 서비스 두개에서 MemoryMemberRepository 객체는 같은 객체 즉, 자바 스프링 빈으로 등록되어 싱글톤으로 관리되는 객체이다.
        // 하지만 새로운 new 로 한 것이랑은 다르다!
        MemberServiceImpl memberServiceTestB = ac.getBean(MemberServiceImpl.class);
        System.out.println("memberServiceTest = " + memberServiceTestA); // 이렇게 test 에서 새롭게 new 로 한 객체는 빈에 등록된 객체랑 다르지
        System.out.println("memberServiceTestB = " + memberServiceTestB);

        System.out.println("memberServiceTestA.getMemoryRepository() = " + memberServiceTestA.getMemoryRepository());
        System.out.println("memberServiceTestB.getMemoryRepository() = " + memberServiceTestB.getMemoryRepository());

        //same 이랑 equal 의 차이
        assertThat(memberServiceTestA).isNotSameAs(memberServiceTestB);
        assertThat(memberServiceTestA.getMemoryRepository()).isNotSameAs(memberServiceTestB.getMemoryRepository());
    }
}
