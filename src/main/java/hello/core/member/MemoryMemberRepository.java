package hello.core.member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements MemoryRepository{
    //동시성 문제가 있을 수 있어서 concurrent hash map 을 사용해야하지만
    //단순 프로젝트로 간단하게 넘길 예정
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public Member save(Member member) {
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(store.get(memberId));
    }
}
