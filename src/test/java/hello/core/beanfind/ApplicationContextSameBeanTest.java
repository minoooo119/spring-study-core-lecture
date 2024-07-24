package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextSameBeanTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 두개 있는 경우 -> 중복 오류")
    void findByTypeDuplicate() {
        // MemberRepository 타입으로 두개의 빈이 등록되어 있으므로 둘 중에 어떤 것을 반환해야 할지 알 수가 없다.
        // 그래서 NoUniqueBeanDefinitionException 오류가 발생하게 된다.
//        ac.getBean(MemberRepository.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회 시 같은 타입 둘 이상인 경우 이름으로 조회한다.")
    void findByName() {
        MemberRepository bean = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(bean).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);

        /** 아래와 같이 출력이 된다.
         * key = memberRepository1 value = hello.core.member.MemoryMemberRepository@765f05af
         * key = memberRepository2 value = hello.core.member.MemoryMemberRepository@62f68dff
         * beansOfType = {memberRepository1=hello.core.member.MemoryMemberRepository@765f05af, memberRepository2=hello.core.member.MemoryMemberRepository@62f68dff}
         */
    }


    @Configuration
    static class SameBeanConfig {
        //static class 를 사용하는 이유 -> scope 밖의 class 내부로 한정 지어서 사용할 수 있게 하려고
        @Bean
        MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

}
