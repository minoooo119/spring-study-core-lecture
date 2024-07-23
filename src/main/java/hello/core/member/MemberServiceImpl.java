package hello.core.member;

import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    //현재는 추상화 구체화 모두 의존하고 있음 DIP 위배하고 있다.
    private final MemberRepository memoryRepository; // 지워주고 생성자를 통해 주입하게 한다.= new MemoryMemberRepository();

    // 생성자를 통해 의존 관계가 만들어 진다. 인터페이스만 남겨둘 수 있도록 하였다.
    // 구체적으로 어떤 구현체인지는 AppConfig 에서 해줄 수 있도록 한다.
    // 생성장 injection 을 배워 보았다.
    public MemberServiceImpl(MemberRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    @Override
    public void join(Member member) {
        memoryRepository.save(member);
    }

    @Override
    public Optional<Member> findMember(Long memberId) {
        return memoryRepository.findById(memberId);
    }
}
