package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기") // 등록된 모든 빈 출력
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + " object= " + bean);
            /**
             * name = org.springframework.context.annotation.internalConfigurationAnnotationProcessor object= org.springframework.context.annotation.ConfigurationClassPostProcessor@7ec3394b
             * name = org.springframework.context.annotation.internalAutowiredAnnotationProcessor object= org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor@bff34c6
             * name = org.springframework.context.annotation.internalCommonAnnotationProcessor object= org.springframework.context.annotation.CommonAnnotationBeanPostProcessor@1522d8a0
             * name = org.springframework.context.event.internalEventListenerProcessor object= org.springframework.context.event.EventListenerMethodProcessor@312ab28e
             * name = org.springframework.context.event.internalEventListenerFactory object= org.springframework.context.event.DefaultEventListenerFactory@5644dc81
             * name = appConfig object= hello.core.AppConfig$$SpringCGLIB$$0@246f8b8b
             * name = memberService object= hello.core.member.MemberServiceImpl@278bb07e
             * name = memberRepository object= hello.core.member.MemoryMemberRepository@4351c8c3
             * name = orderService object= hello.core.order.OrderServiceImpl@3381b4fc
             * name = discountPolicy object= hello.core.discount.RateDiscountPolicy@6bea52d4
             */
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기") //등록한 빈만 출력하게 해줌
    void findApplicationBean() {
        // 스프링에 등록된 모든 빈 이름을 조회한다.
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName); // 빈 이름으로 등록된 객체를 조회한다.
                System.out.println("name = " + beanDefinitionName + " object= " + bean);
            }
            /**
             * name = appConfig object= hello.core.AppConfig$$SpringCGLIB$$0@246f8b8b
             * name = memberService object= hello.core.member.MemberServiceImpl@278bb07e
             * name = memberRepository object= hello.core.member.MemoryMemberRepository@4351c8c3
             * name = orderService object= hello.core.order.OrderServiceImpl@3381b4fc
             * name = discountPolicy object= hello.core.discount.RateDiscountPolicy@6bea52d4
             */
        }
    }
}
