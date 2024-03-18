package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //spring bean 등록
@RequiredArgsConstructor
public class MemberRepository {
    //@PersistenceContext //JPA entity Manager를 spring이 생성한 Entity Manager에 주입
    private final EntityManager em;

    // JPA가 member를 저장하는 로직 구현
    public void save(Member member) {
        em.persist(member); //영속성 컨텍스트에 멤버 엔티티 넣음 -> 트랜잭션이 commit 되는 시점에 db에 반영됨(db에 인서트 쿼리 )
    }

    public Member findOne(Long id) {
        return em.find(Member.class,id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList(); //jpql ,Entity로 탐색함
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
