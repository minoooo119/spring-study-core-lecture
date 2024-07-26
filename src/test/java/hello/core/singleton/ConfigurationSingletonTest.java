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

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        //bean = class hello.core.AppConfig$$SpringCGLIB$$0 이렇게 나온다. 예상대로면 class hello.core.AppConfig 이렇게 나와야함
        //내가 만든 클래스가 아니라 cglib 라는 바이트코드 조작 라이브러리를 사용해서 임의의 다른 클래스를 만들고 빈으로 등록한 것 이다.

        //조작된 클래스가 싱글톤을 보장해줄 것 이다. 내가 만든 클래스만 생각하면 안된다.

        //아마 등록된 객체가 있다면 찾아서 반환하고 아니면 새롭게 만들게 할 것이다.

        //@Configuration 제외하고 @Bean 만 사용하면 memberRepository 가 3번 생성된다... => 빈은 등록이 됨
        //위 상황에서 빈은 등록이 되지만 싱글톤은 보장을 해주지 않는다.
        //그냥 configuration annotation 잘 사용하면 문제가 없다.

        // annotation => configuration 을 통해 appConfig 의 자식 클래스를 활용해서 싱글톤을 보장하게 된다.

    }
}

