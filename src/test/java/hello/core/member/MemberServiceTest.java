package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class MemberServiceTest {

    //    AppConfig appConfig = new AppConfig();
    //    MemberService memberService = new MemberServiceImpl();
// DIP 를 위해 수정
//    MemberService memberService = appConfig.memberService();
    MemberService memberService;

    @BeforeEach
    void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L).get();

        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
