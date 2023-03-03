package handwoong.waiter.customer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {
	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public long getWaitingCount() {
		return customerRepository.count();
	}

	public Customer waitingRegister(Customer customer) {
		Optional<Customer> foundCustomer = customerRepository.findFirstByOrderByWaitingNumberDesc();
		customer.setWaitingNumber(foundCustomer.map(value -> value.getWaitingNumber() + 1).orElse(1L));
		customer.setWaitingTurn(getWaitingCount());
		return customerRepository.save(customer);
	}
}
