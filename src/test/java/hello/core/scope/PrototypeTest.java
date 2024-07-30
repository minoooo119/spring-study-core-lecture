package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {
    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        /**
         * find prototypeBean1
         * PrototypeBean.init
         * find prototypeBean2
         * PrototypeBean.init
         * prototypeBean1 = hello.core.scope.PrototypeTest$PrototypeBean@7a55af6b
         * prototypeBean2 = hello.core.scope.PrototypeTest$PrototypeBean@3d9c13b5
         */
        //init 은 있지만 destroy 는 나오지 않는다. 딱 생성, 의존 관계 주입 까지만 해준다.
        //그래서 콜백함수 종료에 대해서는 수동으로 불러줘야한다.
        ac.close();

    }

    //component 가 없어도 annotation 에 넣어주면 그냥 빈으로 등록을 해버린다.
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

}
