package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    @DisplayName("등록된 빈이 정말 singleton 을 지키는지 확인")
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memoryRepository = ac.getBean("memberRepository", MemberRepository.class);
        MemberRepository memoryRepository1 = memberService.getMemoryRepository();
        MemberRepository memoryRepository2 = orderService.getMemoryRepository();

        System.out.println("memoryRepository = " + memoryRepository);
        System.out.println("memberService -> memoryRepository1 = " + memoryRepository1);
        System.out.println("orderService -> memoryRepository2 = " + memoryRepository2);

        /**
         * 3번의 new 로 3개의 객체가 만들어질 것이라고 생각했던 memoryRepository 그러나 다 같은 객체였다.
         *
         * memoryRepository = hello.core.member.MemoryMemberRepository@2a7686a7
         * memberService -> memoryRepository1 = hello.core.member.MemoryMemberRepository@2a7686a7
         * orderService -> memoryRepository2 = hello.core.member.MemoryMemberRepository@2a7686a7
         */


        // 같은지 테스트
        assertThat(memberService.getMemoryRepository()).isSameAs(memoryRepository);
        assertThat(orderService.getMemoryRepository()).isSameAs(memoryRepository);
    }
}

