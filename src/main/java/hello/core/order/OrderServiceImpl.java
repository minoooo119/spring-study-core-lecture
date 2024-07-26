package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 2개가 필요함
 * 회원을 찾아야하고
 * 할인 정책을 알아봐야함
 */
@Component
public class OrderServiceImpl implements OrderService {

//    private final MemoryRepository memoryRepository = new MemoryMemberRepository();
    //할인 정책을 바꿔서 진행해보자
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    역할과 구현을 충실하게 분리를 잘 했다.
//    그러나 !!!!! OCP, DIP 잘 준수 했다고 보이지만!!

//    DIP : OrderService 는 discountPolicy 를 의존하고 있다. 근데 구현체인 Fix, Rate Discount Policy 까지 의존하게 된다...ㅜ
//          interface 뿐 아니라 구체 class 까지 의존하게 되는 문제라는 것이다. => DIP 위배 (구현체에 의존을 하게 되었으므로..)

    //    OCP : 구현체를 의존하므로 다른 구현체로 변경하게 되는 순간 OrderService 의 코드를 변경해야함. 클라이언트 코드에 영향을 준다..
//          Fix -> Rate 로 변경을 해야하니까.. => OCP 위배 (확장 뿐 아니라 변경까지 허용이 되고 있다.)
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    그래서 아래와 같이 변경을 하게 된다.

//    private DiscountPolicy discountPolicy;
    // 위 처럼 하면 인터페이스에만 의존하게 된다. 그러나 기존 테스트는 통과 못함 인터페이스로는 구현이 안되있으므로..
    // 구현체가 없이 어떻게 코드를 실행할 수 있을까??
    // 해결을 위해서는 누군가 구현 객체를 대신 생성해서 대신 주입해줘야한다!!

    //////////   섹션 3. 객치 지향 원리 적용 => 관심사의 분리 내용
//    클라이언트에서 구현체를 선택하고 그러는 것은 배우가 직접 상대 배우를 케스팅하고 그러는 것과 같다.
//    배우 스스로 공연에 배우 선택까지 많은 책임이 따르게 된다. DIP 가 잘 안된 것이며 잘못된 코드라는 것이다.
//    공연을 구성하고 배우를 지정해주는 것은 => 별도의 공연 기획자가 필요하다.
//    책임을 확실하게 분리하는 것이 매우 중요하다.
//    AppConfig 에서 진행할 => 구현 객체를 생성하고 연결해주는 역할을 수행한다.
//////////   섹션 3. 객치 지향 원리 적용 => 관심사의 분리 내용

    /**
     * section 7. 의존 관계
     * private final 로 되어 있는 경우 값을 넣어줘야한다.
     * null 이 들어가면 안된다.
     */

    /*
     * 1. 생성자 의존 관계 주입
     * 생성자가 하나면 @Autowired 생략해도 된다.
     */
    private final MemberRepository memberRepository; //구현체를 여기서 직접 주지 않기 위해 생성자 만들어라 //= new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy;

    //    //이제 DIP 를 지키게 된다. 구현체 말고 추상화인 인터페이스에만 의존하게 되었다.
//    //외부에서 주입을 해줘야 한다. 어떤 구현체가 들어올지에 대해서는 의존하지 않을 수 있다. 여기서 결정해줄 것은 아니므로
//    /**
//     * section 6. component scan 관련 Autowired 해주기
//     */
    // new OrderServiceImpl() => 어차피 객체가 불릴 때 생성자를 통해 의존 관계가 설정 된다.
    // 수정자는 그 다음 과정에 일어난다.
    @Autowired //스프링 빈으로 등록된 객체는 Autowired 로 자동 주입이 가능하다. 주입 되는 객체도 스프링 빈으로 관리되는 객체들이다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
//        System.out.println("memberRepository = " + memberRepository); // section 7
        this.discountPolicy = discountPolicy;
//        System.out.println("discountPolicy = " + discountPolicy); // section 7
    }

    /*
     * 2. 수정자 의존 관계 주입 (setter)
     *
     *     수정자를 사용하려면 당연하게도 final 을 삭제해야한다.
     *
     *     private MemberRepository memberRepository;
     *     private DiscountPolicy discountPolicy;
     *
     *     Autowired 시 주입 대상이 없으면 오류가 발생한다. 오류 없애려면 아래와 같이 한다.
     *
     *     @Autowired(required = false) //필수가 아니게 하는 것
     *     public void setMemberRepository(MemberRepository memberRepository) {
     *         System.out.println("memberRepository = " + memberRepository);
     *         this.memberRepository = memberRepository;
     *     }
     *
     *     @Autowired
     *     public void setDiscountPolicy(DiscountPolicy discountPolicy) {
     *         System.out.println("discountPolicy = " + discountPolicy);
     *         this.discountPolicy = discountPolicy;
     *     }
     */

    /*
     * 3. 필드 주입 -> 권장되지는 않는 방법이다.
     *
     *      @Autowired private MemberRepository memberRepository;
     *      @Autowired private DiscountPolicy discountPolicy;
     *
     *      어차피 이게 되게 하려면 setter 가 필요하게 됨. 순수하게 자바에서 하려고 하면 불가능 => spring 에서만 가능한 것
     *      DI 프레임워크가 없이는 아무것도 할 수 없게 된다.
     *
     *      springboot 의 test 시에는 사용하면 좋다.
     *      스프링 내부에서 사용하는 경우에만 사용하는게 좋다. 그러면 보통 injection 이 되어서 어렵지 nullPointException 이 발생안함
     */

    /* 4. 일반 메서드 -> 이 것도 굳이 왜 쓰나 싶긴함
     *     private MemberRepository memberRepository;
     *     private DiscountPolicy discountPolicy;
     *
     *     @Autowired
     *     public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
     *         this.memberRepository = memberRepository;
     *         this.discountPolicy = discountPolicy;
     *     }
     */

    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId).get();

        // member 자체를 넘길지 grade 만 넘길지 선택해 줄 수도 있다.
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 싱글톤 테스트용
    public MemberRepository getMemoryRepository() {
        return memberRepository;
    }
}
