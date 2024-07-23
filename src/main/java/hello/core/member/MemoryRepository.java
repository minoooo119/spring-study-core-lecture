package hello.core.member;

import java.util.Optional;

public interface MemoryRepository {
    Member save(Member member);

    Optional<Member> findById(Long memberId);
}