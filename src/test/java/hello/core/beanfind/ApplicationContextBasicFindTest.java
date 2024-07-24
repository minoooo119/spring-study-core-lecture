package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        //이름을 통해 해당 객체 가져오고
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        //해당 객체를 잘 가져왔는지 확인
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        //타입만을 통해 해당 객체 가져오고
        MemberService memberService = ac.getBean(MemberService.class);
        //해당 객체를 잘 가져왔는지 확인
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

        //인터페이스로 조회를 하면 구현체가 대상이 된다.
    }


    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        //이름을 통해 해당 객체 가져오고 타입은 구체 타입으로 설정을 한다.
        //물론 구체화해서 적는 것은 좋지 않다. 역할과 구현은 구분하고 역할에만 의존하게 하는 것이 좋다.
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        //해당 객체를 잘 가져왔는지 확인
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회 실패시")
    void findBeanByNameFail() {
//        이름을 통해 해당 객체 가져오고
//        ac.getBean("xxxx", MemberService.class);
//        MemberService memberService = ac.getBean("xxxx", MemberService.class);
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxx", MemberService.class));
//        뒤에 실행 시에 예외가 터져야 한다고 테스트 진행하는 것임
    }

}
