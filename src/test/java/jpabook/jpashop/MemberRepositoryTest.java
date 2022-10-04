package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class) //스프링부트와 관련되 테스트를 한다는 뜻
@SpringBootTest
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
//    @Rollback(value = false)
    public void testMember() throws Exception {
        Member memeber = new Member();
        memeber.setName("memberA");

        Long saveId = memberRepository.save(memeber);
        Member findMember = memberRepository.find(saveId);

        System.out.println("findMember.getId() = " + findMember.getId());

        Assertions.assertThat(findMember.getId()).isEqualTo(memeber.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(memeber.getName());
        Assertions.assertThat(findMember).isEqualTo(memeber); //같은 ID의 Entity는 같은 객체임

        /*
        테스트 코드에서는 DB에 데이터작업이 일어나도 테스트가 끝나면 다시 롤백을 시켜줌.
        컨피그파일의 Auto DDL 옵션에 따라 테이블이 없으면 자동으로 테이블 까지 생성이 되는데
        그 이후 데이터가 변조 되는 과정은 테스트가 끝나면 롤백됨.

        따라서 위 Long saveId = memberRepository.save(memeber); 실행 코드에 따른 데이터는 테이블에 없음.

        @Rollback(value = false)옵션을 주면 데이터가 롤백 안되고 들어감.
         */

    }

}