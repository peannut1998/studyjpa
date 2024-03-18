package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest //스프링 부트를 띄우고 테스트 하기 위해 추가(스프링 컨테이너 안에서 테스트 수행)
@Transactional  //Test케이스에 @Transactional -> 기본적으로 데이터를 롤백시키기 때문에 db에 insert문을 날리지 않음
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    //@Rollback(false) //commit 시킴으로써 db에 insert문을 날리게 함
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush(); //강제로 db의 영속성 컨텍스트에 있는 쿼리를 db에 날리고 Transactinal이 rollback(insert됐다가 rollback)
        Assert.assertEquals(member,memberRepository.findOne(saveId));

    }

    @Test
    public void 중복회원예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        //then
        fail("예외가 발생해야한다");
    }
}