package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//static import 해주는 것이 좋음
import static org.assertj.core.api.Assertions.*;

class RateDiscountPolicyTest {
    //정말로 10프로 할인이 되는지 확인을 해야한다.
    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP 10프로 할인이 적용되어야한다.")
    void VIP_할인_10() {
        //given
        Member member = new Member(1L, "mino", Grade.VIP);

        //when
        int discount = discountPolicy.discount(member, 10000);

        //then
        assertThat(discount).isEqualTo(1000);
        //10000원의 10프로가 1000원이고 VIP 회원에 대해서 잘 적용되었는지 확인하는 것 -> 성공 테스트임

    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되면 안된다.")
    void NO_VIP_할인_0(){
        //given
        Member member = new Member(1L, "mino", Grade.BASIC);

        //when
        int discount = discountPolicy.discount(member, 10000);

        //then
        assertThat(discount).isEqualTo(0);

    }
}