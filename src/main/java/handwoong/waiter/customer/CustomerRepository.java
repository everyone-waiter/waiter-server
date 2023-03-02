package handwoong.waiter.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
	Optional<Customer> findByWaitingNumber(Long waitingNumber);

	Optional<Customer> findFirstByOrderByWaitingNumberDesc();

	Optional<Customer> findFirstByOrderByWaitingNumberAsc();

	Optional<Customer> deleteById(Long id);

	Optional<Customer> findById(Long id);

	<S extends Customer> S save(S entity);

	long count();

	List<Customer> findAll();
}
