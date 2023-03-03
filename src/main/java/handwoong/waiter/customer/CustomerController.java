package handwoong.waiter.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping
	public String waitingForm(Model model) {
		long waitingCount = customerService.getWaitingCount();
		model.addAttribute("count", waitingCount);
		return "customers/waiting";
	}

	@GetMapping("/reload")
	@ResponseBody
	public long waitingCountReload() {
		return customerService.getWaitingCount();
	}

	@PostMapping
	public String waitingRegister(Customer customer, RedirectAttributes redirectAttributes) {
		log.info("[POST] waitingRegister RequestBody is {}", customer);
		Customer saveCustomer = customerService.waitingRegister(customer);
		redirectAttributes.addFlashAttribute("customer", saveCustomer);
		log.info("[POST] waitingRegister Save Customer is {}", saveCustomer);
		return "redirect:/customers/waiting/result";
	}

	@GetMapping("/result")
	public String registerResult(Customer customer, Model model) {
		model.addAttribute("customer", customer);
		return "customers/result";
	}
}
