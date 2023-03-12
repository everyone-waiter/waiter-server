package handwoong.waiter.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import handwoong.waiter.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
	private final EntityManager em;

	public void save(Member member) {
		em.persist(member);
	}

	public Member findOne(UUID memberId) {
		return em.find(Member.class, memberId);
	}

	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}

	public List<Member> findByEmail(String email) {
		return em.createQuery("select m from Member m where email = :email", Member.class)
				 .setParameter("email", email)
				 .getResultList();
	}
}
