package hello.core.member;

import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    //현재는 추상화 구체화 모두 의존하고 있음 DIP 위배하고 있다.
    private final MemoryRepository memoryRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memoryRepository.save(member);
    }

    @Override
    public Optional<Member> findMember(Long memberId) {
        return memoryRepository.findById(memberId);
    }
}
