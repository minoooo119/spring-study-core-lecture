package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * section 3. spring 으로 전환 내용 추가
 */
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
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        // * section 3. spring 으로 전환 내용 추가
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 위처럼 하면 스프링 컨테이너에 appConfig 안에 있는 빈들을 다 집어넣어서 관리해준다.

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        //위처럼 컨테이너에 있는 빈들을 가져와서 사용하면 된다.

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

