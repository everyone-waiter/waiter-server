package handwoong.waiter.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/customers/waiting")
public class CustomerController {
	private final CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	// ============ VIEW ============
	@GetMapping
	public String waitingForm(Model model) {
		long waitingCount = customerService.getWaitingCount();
		model.addAttribute("count", waitingCount);
		model.addAttribute("customer", new Customer());
		return "customers/waiting";
	}

	@GetMapping("/result")
	public String registerResult(Customer customer, Model model) {
		model.addAttribute("customer", customer);
		return "customers/result";
	}

	@GetMapping("/turn/{customerId}")
	public String waitingTurn(@PathVariable String customerId, Model model) {
		Customer customer = customerService.getWaitingMyTurn(customerId);
		model.addAttribute("customer", customer);
		return "customers/turn";
	}

	@GetMapping("/cancel/{customerId}")
	public String waitingCancel(@PathVariable String customerId, Model model) {
		Customer customer = customerService.getCustomerById(customerId);
		model.addAttribute("customer", customer);
		return "customers/cancel";
	}

	@GetMapping("/admin")
	public String waitingAdmin(Model model) {
		List<Customer> waitingList = customerService.getWaitingList();
		model.addAttribute("customers", waitingList);
		return "customers/admin";
	}

	// ==============================

	@PostMapping
	public String waitingRegister(Customer customer, RedirectAttributes redirectAttributes) {
		log.info("[POST] Waiting Register RequestBody is {}", customer);
		Customer saveCustomer = customerService.waitingRegister(customer);
		redirectAttributes.addFlashAttribute("customer", saveCustomer);
		log.info("[POST] Waiting Register Save Customer is {}", saveCustomer);
		return "redirect:/customers/waiting/result";
	}

	@PostMapping("/notice/{customerId}")
	@ResponseBody
	public String waitingNotice(@PathVariable String customerId) {
		log.info("[POST] Request Send Waiting Notice CustomerId = {}", customerId);
		customerService.enterNotice(customerId);
		log.info("[POST] Response Send Waiting Notice OK CustomerId = {}", customerId);
		return "ok";
	}

	@GetMapping("/reload")
	@ResponseBody
	public long waitingCountReload() {
		return customerService.getWaitingCount();
	}

	@GetMapping("/admin/reload")
	@ResponseBody
	public List<Customer> waitingAdminReload() {
		return customerService.getWaitingList();
	}

	@DeleteMapping("/delete/{customerId}")
	@ResponseBody
	public Customer waitingDelete(@PathVariable String customerId) {
		log.info("[DELETE] Waiting Delete RequestId is {}", customerId);
		Customer deletedCustomer = customerService.deleteWaiting(customerId);
		log.info("[DELETE] Waiting Delete Customer is {}", deletedCustomer);
		return deletedCustomer;
	}

	@DeleteMapping("/cancel/{customerId}")
	@ResponseBody
	public Customer waitingCancel(@PathVariable String customerId) {
		log.info("[DELETE] Waiting Cancel RequestId is {}", customerId);
		Customer deletedCustomer = customerService.cancelWaiting(customerId);
		log.info("[DELETE] Waiting Cancel Customer is {}", deletedCustomer);
		return deletedCustomer;
	}
}
