package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import hello.core.member.MemoryRepository;

public class OrderServiceImpl implements OrderService {
    //2개가 필요함
    // 회원을 찾아야하고
    // 할인 정책을 알아봐야함

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

    private final MemoryRepository memoryRepository; //구현체를 여기서 직접 주지 않기 위해 생성자 만들어라 //= new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy;
    //이제 DIP 를 지키게 된다. 구현체 말고 추상화인 인터페이스에만 의존하게 되었다.
    //외부에서 주입을 해줘야 한다. 어떤 구현체가 들어올지에 대해서는 의존하지 않을 수 있다. 여기서 결정해줄 것은 아니므로

    public OrderServiceImpl(MemoryRepository memoryRepository, DiscountPolicy discountPolicy) {
        this.memoryRepository = memoryRepository;
        this.discountPolicy = discountPolicy;
    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memoryRepository.findById(memberId).get();

        // member 자체를 넘길지 grade 만 넘길지 선택해 줄 수도 있다.
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice,discountPrice);
    }
}
