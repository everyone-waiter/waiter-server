package handwoong.waiter.customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {
	private Long id;
	private Long waitingNumber;
	private Long waitingTurn;
	private int total;
	private int adult;
	private int children;
	private String phoneNumber;
}
