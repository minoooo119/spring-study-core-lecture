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
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId=1L;
        Member member = new Member(memberId, "mino", Grade.VIP);
        memberService.join(member);

        Order computerOrder = orderService.createOrder(member.getId(), "computer", 1700);
        System.out.println("computerOrder price : " + computerOrder.calculatePrice());
        String stringOrder = computerOrder.toString();
        System.out.println("stringOrder = " + stringOrder);

    }
}

