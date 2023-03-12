package handwoong.waiter.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import handwoong.waiter.domain.Member;

@SpringBootTest
@Transactional
class MemberServiceTest {
	private final MemberService memberService;

	@Autowired
	public MemberServiceTest(MemberService memberService) {
		this.memberService = memberService;
	}

	@Test
	@DisplayName("회원가입")
	public void 회원가입() throws Exception {
		// given
		Member member = Member.builder()
							  .email("test@test.com")
							  .name("handwoong")
							  .phoneNumber("01012345678")
							  .build();

		// when
		UUID memberId = memberService.register(member);
		Member saveMember = memberService.findOne(memberId);

		// then
		assertThat(saveMember).isEqualTo(member);
	}

	@Test
	@DisplayName("회원조회")
	public void 회원조회() throws Exception {
		// given
		Member member = Member.builder()
							  .email("test@test.com")
							  .name("handwoong")
							  .phoneNumber("01012345678")
							  .build();

		// when
		UUID memberId = memberService.register(member);
		Member saveMember = memberService.findOne(memberId);

		// then
		assertThat(saveMember.getId()).isEqualTo(member.getId());
		assertThat(saveMember.getEmail()).isEqualTo("test@test.com");
		assertThat(saveMember.getName()).isEqualTo("handwoong");
		assertThat(saveMember.getPhoneNumber()).isEqualTo("01012345678");
	}

	@Test
	@DisplayName("회원목록_조회")
	public void 회원목록_조회() throws Exception {
		// given
		Member member1 = Member.builder()
							   .email("test@test.com")
							   .name("handwoong")
							   .phoneNumber("01012345678")
							   .build();
		Member member2 = Member.builder()
							   .email("test@test.com")
							   .name("handwoong")
							   .phoneNumber("01012345678")
							   .build();

		// when
		memberService.register(member1);
		memberService.register(member2);
		List<Member> members = memberService.findMembers();

		// then
		assertThat(members.size()).isEqualTo(2);
		assertThat(members).contains(member1);
		assertThat(members).contains(member2);
	}
}
