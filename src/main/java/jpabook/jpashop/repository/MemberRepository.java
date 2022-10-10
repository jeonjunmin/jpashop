package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext //JPA의 엔티티 매니저 선언
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class) //JPQL과 SQL쿼리의 차이점은 테이블이 아닌 엔티티 대상으로 쿼리를 한다.
                .getResultList();

        return result;
    }

    public List<Member> findByName(String name){
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result;
    }

}
