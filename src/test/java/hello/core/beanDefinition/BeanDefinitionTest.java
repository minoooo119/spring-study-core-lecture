package hello.core.beanDefinition;

import ch.qos.logback.core.joran.GenericXMLConfigurator;
import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

//    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                        " beanDefinition = " + beanDefinition);
            }
            // 스프링 컨테이너는 bean definition 을 확인해서 빈을 등록을 한다.
            // 그래서 빈을 등록할 때에는 두가지 방법. 1. 직접 definition 을 추가한다. 2. factory bean 을 활용한다. 로 나뉜다.
            // java config 를 통해 하는 것은 후자에 해당된다.
            // 어쨋든 다양한 설정 정보를 bean definition 으로 추상화해서 확인한다고 이해하자.


            // 직접 bean definition 을 새롭게 추가할 수도 있으나 그럴일은 전혀 없다 -> 그냥 설정 파일에 적어두고 reader 가 읽어서 적을 수 있게 한다.
            // 다양한 형태의 설정 정보를 BeanDefinition 으로 추상화해서 사용하는 것 정도로만 이애하면 된다.

            /** Java Config 를 통해 진행 -> factory method 와 관련이 있다. annotation config 를 하면 factory bean 을 통해 등록이 된다.
             * beanDefinitionName = appConfig
             * beanDefinition = Generic bean: class [hello.core.AppConfig$$SpringCGLIB$$0]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null
             * beanDefinitionName = memberService
             * beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=memberService; initMethodNames=null; destroyMethodNames=[(inferred)]; defined in hello.core.AppConfig
             * beanDefinitionName = memberRepository
             * beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=memberRepository; initMethodNames=null; destroyMethodNames=[(inferred)]; defined in hello.core.AppConfig
             * beanDefinitionName = orderService
             * beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=orderService; initMethodNames=null; destroyMethodNames=[(inferred)]; defined in hello.core.AppConfig
             * beanDefinitionName = discountPolicy
             * beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=discountPolicy; initMethodNames=null; destroyMethodNames=[(inferred)]; defined in hello.core.AppConfig
             */

            /** XML Config 를 통해 진행
             * beanDefinitionName = memberService
             * beanDefinition = Generic bean: class [hello.core.member.MemberServiceImpl]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null; defined in class path resource [appConfig.xml]
             * beanDefinitionName = memberRepository
             * beanDefinition = Generic bean: class [hello.core.member.MemoryMemberRepository]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null; defined in class path resource [appConfig.xml]
             * beanDefinitionName = orderService
             * beanDefinition = Generic bean: class [hello.core.order.OrderServiceImpl]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null; defined in class path resource [appConfig.xml]
             * beanDefinitionName = discountPolicy
             * beanDefinition = Generic bean: class [hello.core.discount.RateDiscountPolicy]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null; defined in class path resource [appConfig.xml]
             */
        }
    }
}
