package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl(null);
//        OrderService orderService = new OrderServiceImpl(null,null);
//
//        Long memberId=1L;
//        Member member = new Member(memberId, "mino", Grade.VIP);
//        memberService.join(member);
//
//        Order computerOrder = orderService.createOrder(member.getId(), "computer", 1700);
//        System.out.println("computerOrder price : " + computerOrder.calculatePrice());
//        String stringOrder = computerOrder.toString();
//        System.out.println("stringOrder = " + stringOrder);


        //version 2 -> DIP 만족하는 상황에서 테스트 진행

        //given
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        //when
        Long memberId = 1L;
        Member mino = new Member(memberId, "mino", Grade.VIP);
        memberService.join(mino);
        Order itemA = orderService.createOrder(memberId, "itemA", 10000);

        //them
        System.out.println("price = " + 10000*9/10);
        System.out.println("price = " + itemA.calculatePrice());
        System.out.println("itemA = " + itemA);

    }
}

