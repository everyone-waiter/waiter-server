package handwoong.waiter.exception;

public class NotFoundWaitingException extends RuntimeException {

	public NotFoundWaitingException() {
		super("웨이팅을 찾을 수 없습니다.");
	}

	public NotFoundWaitingException(String message) {
		super(message);
	}

	public NotFoundWaitingException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundWaitingException(Throwable cause) {
		super(cause);
	}
}
