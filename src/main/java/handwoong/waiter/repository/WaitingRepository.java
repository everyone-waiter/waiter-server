package handwoong.waiter.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import handwoong.waiter.domain.Waiting;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WaitingRepository {

	private final EntityManager em;

	public void save(Waiting waiting) {
		em.persist(waiting);
	}

	public Waiting findOne(UUID waitingId) {
		return em.find(Waiting.class, waitingId);
	}

	public List<Waiting> findAll(UUID memberId) {
		return em.createQuery("select w from Waiting w where w.member.id = :memberId", Waiting.class)
				 .setParameter("memberId", memberId)
				 .getResultList();
	}
}
