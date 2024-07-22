package hello.core.member;

import java.util.Optional;

public class MemberServiceImpl implements MemberService {

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
