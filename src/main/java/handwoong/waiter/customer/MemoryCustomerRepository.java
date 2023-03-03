package handwoong.waiter.customer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class MemoryCustomerRepository implements CustomerRepository {
	private final Map<String, Customer> memoryRepository = new ConcurrentHashMap<>();

	@Override
	public Optional<Customer> findByWaitingNumber(Long waitingNumber) {
		return memoryRepository.values()
							   .stream()
							   .filter(customer -> customer.getWaitingNumber().equals(waitingNumber))
							   .findAny();
	}

	@Override
	public Optional<Customer> findFirstByOrderByWaitingNumberDesc() {
		return memoryRepository.values()
							   .stream()
							   .sorted(Comparator.comparing(Customer::getWaitingNumber).reversed())
							   .findAny();
	}

	@Override
	public Optional<Customer> findFirstByOrderByWaitingNumberAsc() {
		return memoryRepository.values()
							   .stream()
							   .sorted(Comparator.comparing(Customer::getWaitingNumber))
							   .findAny();
	}

	@Override
	public Optional<Customer> deleteById(String id) {
		Customer removeCustomer = memoryRepository.remove(id);
		return Optional.of(removeCustomer);
	}

	@Override
	public Optional<Customer> findById(String id) {
		Customer customer = memoryRepository.get(id);
		return Optional.of(customer);
	}

	@Override
	public <S extends Customer> S save(S entity) {
		entity.setId(UUID.randomUUID().toString());
		memoryRepository.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public long count() {
		return memoryRepository.size();
	}

	@Override
	public List<Customer> findAll() {
		return new ArrayList<>(memoryRepository.values());
	}
}
