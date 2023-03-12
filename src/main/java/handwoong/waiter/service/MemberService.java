package handwoong.waiter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import handwoong.waiter.domain.Member;
import handwoong.waiter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	@Transactional
	public UUID register(Member member) {
		// TODO 중복 이메일 유효성 검사
		memberRepository.save(member);
		return member.getId();
	}

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findOne(UUID memberId) {
		return memberRepository.findOne(memberId);
	}
}
