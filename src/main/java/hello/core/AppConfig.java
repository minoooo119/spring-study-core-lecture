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

//의 존 관 계 에 대한 고민은 외부에 맡기고 -> 그 외부가 AppConfig 임
//실 행 에 만 집중한다.

// 의존 관계를 마치 의존에서 주입해주는 것 같다고 해서 -> dependency 주입
// 의존 관계 주입 또는 의존성 주입 이라고 한더.

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

    public MemberService memberService(){
        //생성자 injection
        return new MemberServiceImpl(memberRepository());
    }

    //이렇게 리펙토링 해야한다. -> 역할을 확실하게 알기 위해서이다. return 은 인터페이스로 하는 것이 맞다.
    private static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    //이렇게 리펙토링 해야한다. -> 역할을 확실하게 알기 위해서이다. return 은 인터페이스로 하는 것이 맞다.
    private static DiscountPolicy discountPolicy() {
        //어떤 정책을 선택할지 결정한다.
        //어떤 구현체를 반환하느냐에 따라서 결정할 수 있다.
        //정책 수정에도 이 한줄만 수정할 수 있다는 것이 엄청난 장점이다.
        //구성 영역 외에 사용 영역은 어떠한 변경도 필요 없다.
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
