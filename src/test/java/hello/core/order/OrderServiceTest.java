package hello.core.order;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();
    @Test
    void 주문(){
        //given
        Long memberId=1L;
        Member mino = new Member(memberId, "mino", Grade.VIP);
        memberService.join(mino);

        //when
        Order itemA = orderService.createOrder(memberId, "itemA", 10000);

        //then
        Assertions.assertThat(itemA.calculatePrice()).isEqualTo(9000);
    }
}