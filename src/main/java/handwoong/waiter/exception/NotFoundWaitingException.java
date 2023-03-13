package handwoong.waiter.exception;

public class NotFoundWaitingException extends RuntimeException {

	public NotFoundWaitingException() {
		super();
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
