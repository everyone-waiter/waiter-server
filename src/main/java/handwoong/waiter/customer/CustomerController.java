package handwoong.waiter.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/customers/waiting")
public class CustomerController {
	@GetMapping
	public String waitingList() {
		return "customers/index";
	}
}
