package hello.core.member;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 * section 6. component scan 관련
 */
@Component
@Primary //현재 AppConfig 에 수동으로 등록되어 있으므로 이렇게 해준다.
public class MemoryMemberRepository implements MemberRepository {
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
