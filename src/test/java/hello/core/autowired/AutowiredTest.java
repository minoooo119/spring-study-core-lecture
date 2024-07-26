package hello.core.autowired;

import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutoWiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
        //config class 말고 이거 넣어도 등록됨 그냥

    }

    static class TestBean {

        //Member 는 당연히 스프링 빈이 아니지요!

        @Autowired(required = false)
        public void serNoBean1(Member noBean1) {// 컨테이너에 없는 객체 막 넣음
            System.out.println("noBean1 = " + noBean1);
            // 호출 자체가 안된다.
        }

        @Autowired
        public void serNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
            //noBean2 = null
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
            //noBean3 = Optional.empty
        }
    }
}
