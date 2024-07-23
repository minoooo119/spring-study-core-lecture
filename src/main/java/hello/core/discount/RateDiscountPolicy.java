package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy{
    //vip 인 고객에게는 가격에 고정 된 할인 금액을 적용하는 것이 아니라
    //10% 할인을 해주는 것으로 설정
    final int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP){
            //이 로직이 불안함...
            return price * discountPercent / 100;
        }else{
            return 0;
        }
    }
}
