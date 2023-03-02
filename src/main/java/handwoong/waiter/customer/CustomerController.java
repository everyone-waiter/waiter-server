package handwoong.waiter.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/customers/waiting")
public class CustomerController {
	@GetMapping
	public String waitingForm() {
		return "customers/waiting";
	}

	@PostMapping
	public String waitingRegister(Customer customer, RedirectAttributes redirectAttributes) {
		log.info("[POST] waitingRegister RequestBody is {}", customer);
		redirectAttributes.addAttribute("adult", customer.getAdult());
		redirectAttributes.addAttribute("children", customer.getChildren());
		redirectAttributes.addAttribute("status", "success");
		return "redirect:/customers/waiting/result";
	}

	@GetMapping("/result")
	public String registerResult() {
		return "customers/result";
	}
}
