package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {
    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean( MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
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
}
