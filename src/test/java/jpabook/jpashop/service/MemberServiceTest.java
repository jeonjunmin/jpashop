package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    /**
     * 회원가입 테스트
     */
    @Test
//    @Rollback(value = false) //이 옵션 주면 실제로 DB에 데이터가 들어감
    public void 회원가입() throws Exception {
        //given
        Member member= new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId)); //저정한 member와 찾아온 member가 같은 지 테스트

    }

    /**
     * 중복회원 검증 테스트
     */
    @Test
    public void 회원검증() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("Kim1");

        Member member2 = new Member();
        member2.setName("Kim1");

        //when
        memberService.join(member1);

        //then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());

    }


}