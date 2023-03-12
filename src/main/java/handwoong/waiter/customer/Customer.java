package handwoong.waiter.customer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {
	private String id;

	@Null
	private Long waitingNumber;

	@Null
	private Long waitingTurn;

	@Min(0)
	private int adult;

	@Min(0)
	private int children;

	@Pattern(regexp = "^(01[016789]{1})[0-9]{4}[0-9]{4}$")
	private String phoneNumber;

	private boolean isSendMessage = false;
}
