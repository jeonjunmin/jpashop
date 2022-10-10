package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(readOnly = true) //기본 적으로 트랜잭션 안에서 서비스 로직이 진행되어야 한다. / readOnly 옵션을 주면 조회쿼리 서비스 실행시 성능을 올려준다.
@RequiredArgsConstructor //final로 선언된 객체를 생성자 주입으로 모두 자동 @Autowired 해준다.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional //기본 적으로 트랜잭션 안에서 서비스 로직이 진행되어야 한다. / readOnly는 조회성 함수에만 적용이되기 때문에 위에 붙였기 때문에 따로 안붙여도 되지만 그 외 함수는 @Transactional을 붙여준다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //증복회원 검증
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
