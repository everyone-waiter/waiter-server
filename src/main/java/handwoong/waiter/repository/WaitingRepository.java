package handwoong.waiter.repository;

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
}
