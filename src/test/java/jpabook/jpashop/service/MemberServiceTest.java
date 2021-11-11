package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepository memberRepository;


  @Test
  @DisplayName("회원 가입 테스트")
  void 회원가입() throws Exception {
    //given
    Member member = new Member();
    member.setName("kim");

    //when
    Long savedId = memberService.join(member);

    //then
    //import static org.junit.jupiter.api.Assertions.*;
    assertEquals(member, memberRepository.findOne(savedId));

  }

  @Test
  @DisplayName("중복 회원 예외 발생 테스트")
  void 중복_회원_예외() throws Exception {
    //given
    Member member1 = new Member();
    member1.setName("kim1");

    Member member2 = new Member();
    member2.setName("kim1");

    //when
    memberService.join(member1);

    //then
    assertThrows(IllegalStateException.class, () -> memberService.join(member2));

  }

}