package handwoong.waiter.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import handwoong.waiter.domain.Waiting;
import handwoong.waiter.domain.WaitingStatus;
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
		return em.createQuery("select w from Waiting w where w.member.id = :memberId and w.status = :status", Waiting.class)
				 .setParameter("memberId", memberId)
				 .setParameter("status", WaitingStatus.DEFAULT)
				 .getResultList();
	}

	public Long count(UUID memberId) {
		return em.createQuery("select count(w) from Waiting w where w.member.id = :memberId and w.status = :status", Long.class)
				 .setParameter("memberId", memberId)
				 .setParameter("status", WaitingStatus.DEFAULT)
				 .getSingleResult();
	}
}
