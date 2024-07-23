package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import hello.core.member.MemoryRepository;

public class OrderServiceImpl implements OrderService {
    //2개가 필요함
    // 회원을 찾아야하고
    // 할인 정책을 알아봐야함

    private final MemoryRepository memoryRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memoryRepository.findById(memberId).get();

        // member 자체를 넘길지 grade 만 넘길지 선택해 줄 수도 있다.
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice,discountPrice);
    }
}
