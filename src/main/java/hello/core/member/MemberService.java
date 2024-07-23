package hello.core.member;

import java.util.Optional;

public interface MemberService {
    /**
     * @param member 회원 가입
     */
    void join(Member member);

    /**
     * @param memberId 회원 검색
     * @return id로 검색한 회원 반환
     */
    Optional<Member> findMember(Long memberId);
}

