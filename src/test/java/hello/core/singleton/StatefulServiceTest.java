package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    // given
    // when
    // then

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        //ThreadA: mino 가 1000원 주문
        statefulService1.order("mino", 1000);
        //ThreadB: seohyun 이 2000원 주문
        statefulService2.order("seohyun", 2000);

        //ThreadA: mino 가 가격 조회
        int price1 = statefulService1.getPrice();
        System.out.println("price1 = " + price1);
        //ThreadB: seohyun 이 가격 조회
        int price2 = statefulService2.getPrice();
        System.out.println("price2 = " + price2);
        /**
         * price1 = 2000
         * price2 = 2000
         */

        // 다음 주문한 사람이 덮어쓰게 되는 경우 테스트...
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(price2);

        //StatefulService 에서 price 는 공유되는 변수이다.
        //그래서 문제가 된다!!
        //실무에서는 정말 크나큰 문제가 터지게 된다.
        //공유 필드는 조심해야한다.

        // 해결 -> 클라이언트마다 지역변수를 활용해서 진행하는 방법이 있다.
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}