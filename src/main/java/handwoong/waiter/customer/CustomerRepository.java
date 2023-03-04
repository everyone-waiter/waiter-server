package handwoong.waiter.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
	Optional<Customer> findByWaitingNumber(Long waitingNumber);

	Optional<Customer> findFirstByOrderByWaitingNumberDesc();

	Optional<Customer> findFirstByOrderByWaitingNumberAsc();

	Optional<Customer> deleteById(String id);

	Optional<Customer> findById(String id);

	Customer updateById(String id, Customer customer);

	<S extends Customer> S save(S entity);

	long count();

	List<Customer> findAll();
}
