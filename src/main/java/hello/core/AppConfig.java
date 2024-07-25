package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.member.MemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//의 존 관 계 에 대한 고민은 외부에 맡기고 -> 그 외부가 AppConfig 임
//실 행 에 만 집중한다.

// 의존 관계를 마치 의존에서 주입해주는 것 같다고 해서 -> dependency 주입
// 의존 관계 주입 또는 의존성 주입 이라고 한더.


/**
 * section3. spring 으로 전환
 * 이제 부터 Spring 을 사용해 볼 것이다 @Configuration, @Bean 추가하면 된다.
 * Bean 들은 자동으로 spring container 에 등록이 된다.
 */
@Configuration
public class AppConfig {
    // 인터페이스는 어떤 구현체를 사용할 것이며
    // 어떤 의존 관계를 가지고 있는지
    // 여기서 지정해준다.

    // 이렇게 config 를 통해 의존 관계를 주입 => 각 서비스는 역할에 대해서 기능만 수행하면 된다.
    // config 에서 누가 어떻게 동작할지 구성될 지를 책임진다.
    // 나머지는 어떻게 구현체가 구성이 되어있고 몰라도 되고 인터페이스만 의존해서 역할을 수행하면 된다.
    // 의존 관계에서 변경이 있을 경우에는 AppConfig 내용을 변경해서 진행하면 된다.

    // 이렇게 함으로써 OCP = open close principle, DIP = dependency inversion principle 2가지 원칙을 지킬 수 있다.

    //역할과 구현 클래스를 한눈에 볼 수 있도록 아래처럼 리펙토링을 할 수 있다.
    @Bean //* section3. spring 으로 전환
    public MemberService memberService(){
        // section 5. configuration singleton 관련
        System.out.println("call AppConfig.memberService");

        //생성자 injection
        return new MemberServiceImpl(memberRepository());
    }

    @Bean //* section3. spring 으로 전환
    //이렇게 리펙토링 해야한다. -> 역할을 확실하게 알기 위해서이다. return 은 인터페이스로 하는 것이 맞다.
    public MemberRepository memberRepository() {
        // section 5. configuration singleton 관련
        System.out.println("call AppConfig.memberRepository");

        return new MemoryMemberRepository();
    }

    @Bean //* section3. spring 으로 전환
    public OrderService orderService(){
        // section 5. configuration singleton 관련
        System.out.println("call AppConfig.orderService");

        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean //* section3. spring 으로 전환
    //이렇게 리펙토링 해야한다. -> 역할을 확실하게 알기 위해서이다. return 은 인터페이스로 하는 것이 맞다.
    public DiscountPolicy discountPolicy() {
        //어떤 정책을 선택할지 결정한다.
        //어떤 구현체를 반환하느냐에 따라서 결정할 수 있다.
        //정책 수정에도 이 한줄만 수정할 수 있다는 것이 엄청난 장점이다.
        //구성 영역 외에 사용 영역은 어떠한 변경도 필요 없다.
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

    /**
     * section 5. singleton 관련
     * @Bean memberService -> new MemoryMemberRepository()
     * @Bean orderService -> new MemoryMemberRepository()
     * 이렇게 의존 관계가 되어있으니까 singleton 이 깨져서 두 객체가 생성되는 것 아닌가?
     * test 가 필요하다. => 싱글톤이 깨지는지 아닌지!
     */


    /**
     * call AppConfig.memberService -> call AppConfig.memberRepository
     * call AppConfig.memoryRepository
     * call AppConfig.orderService -> call AppConfig.memberRepository
     *
     * 총 memberRepository 3번 호출 예상이 된다만..?
     *
     * call AppConfig.memberService
     * call AppConfig.memberRepository
     * call AppConfig.orderService
     * 이렇게가 끝이다..
     *
     * 정말 singleton 을 보장해주는구나!!
     */
}
