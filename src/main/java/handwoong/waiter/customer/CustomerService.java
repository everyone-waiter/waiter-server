package handwoong.waiter.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import handwoong.waiter.message.MessageTemplate;
import handwoong.waiter.message.TemplateType;
import lombok.extern.slf4j.Slf4j;
import sens.MessageService;
import sens.MessageType;
import sens.request.MessageBody;
import sens.response.MessageResponse;

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
		Customer saveCustomer = customerRepository.save(customer);
		sendAlimTalk(saveCustomer, TemplateType.REGISTER);
		return saveCustomer;
	}

	public Customer getCustomerById(String id) {
		return customerRepository.findById(id).orElseThrow();
	}

	public Customer deleteWaiting(String id) {
		return customerRepository.deleteById(id).orElseThrow();
	}

	public Customer cancelWaiting(String customerId) {
		Customer deleteCustomer = deleteWaiting(customerId);
		sendAlimTalk(deleteCustomer, TemplateType.CANCEL);
		return deleteCustomer;
	}

	public void enterNotice(String customerId) {
		Customer foundCustomer = getCustomerById(customerId);
		sendAlimTalk(foundCustomer, TemplateType.ENTER);

		Customer firstCustomer = customerRepository.findFirstByOrderByWaitingNumberAsc().orElseThrow();
		if (!isFindCustomerFirst(foundCustomer, firstCustomer))
			return;
		customerRepository.findByWaitingNumber(firstCustomer.getWaitingNumber() + 2)
						  .ifPresent(this::sendEnterReady);
	}

	private void sendEnterReady(Customer thirdCustomer) {
		if (thirdCustomer.isReceiveThirdMessage()) {
			return;
		}
		sendAlimTalk(thirdCustomer, TemplateType.READY);
		thirdCustomer.setReceiveThirdMessage(true);
		customerRepository.updateById(thirdCustomer.getId(), thirdCustomer);
	}

	private boolean isFindCustomerFirst(Customer foundCustomer, Customer firstCustomer) {
		if (firstCustomer.getWaitingNumber().equals(foundCustomer.getWaitingNumber())) {
			return true;
		}
		return false;
	}

	public void sendAlimTalk(Customer customer, TemplateType type) {
		MessageBody messageBody = new MessageTemplate(customer).createTemplate(type);
		MessageResponse response = messageService.send(MessageType.ALIMTALK, messageBody);
		log.info("[Send AlimTalk] Customer PhoneNumber = {}, StatusCode = {}, StatusName = {}",
			customer.getPhoneNumber(), response.getStatusCode(), response.getStatusName());
	}
}
