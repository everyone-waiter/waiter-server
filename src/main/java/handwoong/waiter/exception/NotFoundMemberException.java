package handwoong.waiter.exception;

public class NotFoundMemberException extends RuntimeException {

	public NotFoundMemberException() {
		super("멤버를 찾을 수 없습니다.");
	}

	public NotFoundMemberException(String message) {
		super(message);
	}

	public NotFoundMemberException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundMemberException(Throwable cause) {
		super(cause);
	}
}
