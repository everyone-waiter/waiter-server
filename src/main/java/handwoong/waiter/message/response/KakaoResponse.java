package handwoong.waiter.message.response;

import java.util.List;

public class KakaoResponse {
	private int statusCode;
	private String statusName;
	private List<KakaoResponseMessage> messages;

	public KakaoResponse() {
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<KakaoResponseMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<KakaoResponseMessage> messages) {
		this.messages = messages;
	}

	public static class KakaoResponseMessage {
		private String requestStatusCode;
		private String requestStatusName;
		private String requestStatusDesc;

		public KakaoResponseMessage() {
		}

		public String getRequestStatusCode() {
			return requestStatusCode;
		}

		public String getRequestStatusName() {
			return requestStatusName;
		}

		public String getRequestStatusDesc() {
			return requestStatusDesc;
		}

		public void setRequestStatusCode(String requestStatusCode) {
			this.requestStatusCode = requestStatusCode;
		}

		public void setRequestStatusName(String requestStatusName) {
			this.requestStatusName = requestStatusName;
		}

		public void setRequestStatusDesc(String requestStatusDesc) {
			this.requestStatusDesc = requestStatusDesc;
		}
	}
}
