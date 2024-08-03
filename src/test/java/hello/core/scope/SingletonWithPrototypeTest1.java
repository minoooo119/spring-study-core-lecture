package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);



//        prototypeBean2.addCount();
//        int count = prototypeBean2.getCount();
//        System.out.println("count = " + count);
    }

//    @Test
//    void singletonClientUserPrototype() {
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
//        ClientBean clientBean1 = ac.getBean(ClientBean.class);
//        int count1 = clientBean1.logic();
//        assertThat(count1).isEqualTo(1);
//
//        ClientBean clientBean2 = ac.getBean(ClientBean.class);
//        int count2 = clientBean2.logic();
//        //아니 왜 프로토타입 빈이 새로 안 만들어지고 싱글톤처럼 되는거지?
//        assertThat(count2).isEqualTo(2);
//    }

    @Test
    void singletonClientUserPrototype1() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        //ObjectProvider 로 하면 이렇게 매번 새로운 프로토타입빈을 반환받는다.
        assertThat(count2).isEqualTo(1);

//        int count3 = clientBean2.logic();
//        System.out.println("count3 = " + count3); -> 이것도 1이라고 뜸, 걍 logic 실행마다 새로 만드니까...?
    }

    @Scope("singleton")
    static class ClientBean{
        //이건 clientBean 생성하고 프로토타입 빈이 이때 지정되므로 계속 새로 생성되지 않고 싱글톤처럼 수행된다.
//        private final PrototypeBean prototypeBean; //생성시점에 주입이 된다. -> 계속 같은 것으로 사용? 컨테이너가 관리안하는 것 아닌가.?
        //원하는대로 하고 싶으려면 -> logic 수행마다 컨테이너에서 getBean 을 해주는 방법이 있다.
        /**
         * 이런 방식이다
         *
         * @Autowired private ApplicationContext ac;
         * public int logic() {
         * PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
         * prototypeBean.addCount();
         * int count = prototypeBean.getCount();
         * return count;
         * }
         */
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
        //절대 좋은 방식이 아님 -> 해결 방법 필요함

        /**
         * ObjectFactory, ObjectProvider
         * 'ObjectProvider' 의 `getObject()` 를 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환 한다. (**DL**)
         * 스프링에 의존
         */
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

        /**
         * JSR-330 자바 표준을 사용하는 방법
         * 'Provider' 는 지금 딱 필요한 DL 정도의 기능만 제공한다.
         * ObjectProvider -> Provider 사용
         * 함수도 .getObject() -> .get() 사용
         */
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        /**
         * 다른 프로토타입을 매번 가져오기 위해 ObjectProvider 를 사용한다.
         */
        public int logic() {
            //provider 가 딱 찾아줘서 제공을 해준다. -> 이 시점에 요청을 할 수 있다.
//            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    @Getter
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init: " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
