package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {
    @Test
    @DisplayName("스프링이 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        //1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른 것 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        /**
         * 다른 객체가 생성되는 것을 확인할 수 있습니다....매 호출마다 새롭게 객체를 형성하는 overhead 발생...
         * memberService1 = hello.core.member.MemberServiceImpl@646be2c3
         * memberService2 = hello.core.member.MemberServiceImpl@797badd3
         */

        // memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
        // assertThat(memberService1).isNotEqualTo(memberService2);

        // 해결을 위해서는 딱 하나의 객체만 생성하고 공유하는 싱글톤 패턴을 활용해야한다.

    }

    @Test
    @DisplayName("싱글톤이 맞는지 확인")
    void singletonServiceTest() {
//        new SingletonService(); private 라서 금지 된다.
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        /** 같은 객체를 가져오게 된다.
         * singletonService1 = hello.core.singleton.SingletonService@38364841
         * singletonService2 = hello.core.singleton.SingletonService@38364841
         **/

        assertThat(singletonService1).isSameAs(singletonService2);
        /**
         * same ->  ==
         * 대상의 주소값비교 아예 같은 존재인지
         * equal ->  equals()
         * 대상의 내용만 비교 그냥 같은지 다른지 정도
         * */
    }

    /**
     * 스프링으로 하면 스프링 컨테이너가 빈을 등록할 때 싱글톤으로 등록을 한다.
     * 등록할 때 빈 이름 빈 객체로 저장을 하게 되는데 이때 이 객체가 싱글톤으로 관리된다는 것이다. => 이런 기능이 싱글톤 레지스트리라고 한다.
     * 스프링 컨테이너에 등록된 객체는 싱글톤으로 관리가 된다.
     */
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        /*
            같은 객체를 공유하게 된다. => 스프링 컨테이너는 등록된 빈 객체를 싱글톤으로 관리를 하기 때문이다.
            그러나!!! 싱글톤만 제공하지는 않고 다른 것도 존재한다더라 -> 그치만 대부분 싱글톤으로 관리를 한다.

            memberService1 = hello.core.member.MemberServiceImpl@2a7686a7
            memberService2 = hello.core.member.MemberServiceImpl@2a7686a7
         */

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //memberService1 == memberService2
        assertThat(memberService1).isSameAs(memberService2);
    }
}
