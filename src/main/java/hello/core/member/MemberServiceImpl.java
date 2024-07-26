package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
/**
 * section 6. component scan 관련
 */
@Component
public class MemberServiceImpl implements MemberService {

    //현재는 추상화 구체화 모두 의존하고 있음 DIP 위배하고 있다.
    private final MemberRepository memberRepository; // 지워주고 생성자를 통해 주입하게 한다.= new MemoryMemberRepository();

    // 생성자를 통해 의존 관계가 만들어 진다. 인터페이스만 남겨둘 수 있도록 하였다.
    // 구체적으로 어떤 구현체인지는 AppConfig 에서 해줄 수 있도록 한다.
    // 생성장 injection 을 배워 보았다.

    /**
     * section 6. component scan 관련
     */
    @Autowired //이걸 생성자에 붙여주면 의존 관계 주입을 알아서 해주고 -> MemberServiceImpl 빈으로 등록시 memberRepository 빈 찾아서 등록해줌
    //componentScan 을 하기 위해서는 의존 관계 주입을 위해서는 Autowired 로 자동 의존관계 주입을 해야한다.
    // Spring 에서 의존성을 주입하는 방법에는 생성자 주입, @Autowired, 필드주입 3가지 방식
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Optional<Member> findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //싱글톤 테스트 용
    public MemberRepository getMemoryRepository() {
        return memberRepository;
    }
}
