package handwoong.waiter.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaitingForm {

	@Min(0)
	private int adult;

	@Min(0)
	private int children;

	@Pattern(regexp = "^(01[016789]{1})[0-9]{4}[0-9]{4}$")
	private String phoneNumber;
}
