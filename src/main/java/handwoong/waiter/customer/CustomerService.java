package handwoong.waiter.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import handwoong.waiter.message.MessageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {
	private final CustomerRepository customerRepository;
	private final MessageService messageService;

	@Autowired
	public CustomerService(CustomerRepository customerRepository, MessageService messageService) {
		this.customerRepository = customerRepository;
		this.messageService = messageService;
	}

	public List<Customer> getWaitingList() {
		return customerRepository.findAll();
	}

	public long getWaitingCount() {
		return customerRepository.count();
	}

	public Customer getWaitingMyTurn(String id) {
		Customer customer = customerRepository.findById(id).orElseThrow();
		Customer firstCustomer = customerRepository.findFirstByOrderByWaitingNumberAsc().orElseThrow();

		if (firstCustomer.getId().equals(id)) {
			customer.setWaitingTurn(0L);
		} else {
			customer.setWaitingNumber(customer.getWaitingNumber() - firstCustomer.getWaitingNumber());
		}
		return customer;
	}

	public Customer waitingRegister(Customer customer) {
		Optional<Customer> foundCustomer = customerRepository.findFirstByOrderByWaitingNumberDesc();
		customer.setWaitingNumber(foundCustomer.map(value -> value.getWaitingNumber() + 1).orElse(1L));
		customer.setWaitingTurn(getWaitingCount());
		return customerRepository.save(customer);
	}

	public Customer getCustomerById(String id) {
		return customerRepository.findById(id).orElseThrow();
	}

	public Customer deleteWaiting(String id) {
		return customerRepository.deleteById(id).orElseThrow();
	}

	public Customer cancelWaiting(String customerId) {
		Customer deleteCustomer = deleteWaiting(customerId);
		// TODO 취소 메시지 전송
		return deleteCustomer;
	}
}
