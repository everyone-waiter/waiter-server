package handwoong.waiter.message.request.kakao;

import java.util.List;

import handwoong.waiter.message.request.MessageBody;
import handwoong.waiter.message.request.kakao.messages.KaKaoMessage;

public class KakaoBody implements MessageBody {
	private final String plusFriendId;
	private final List<KaKaoMessage> messages;
	private final String templateCode;
	private final String reserveTime;
	private final String reserveTimeZone;
	private final String scheduleCode;

	public static class Builder {
		private final String plusFriendId;
		private final List<KaKaoMessage> messages;
		private String templateCode;
		private String reserveTime;
		private String reserveTimeZone;
		private String scheduleCode;

		public Builder(String plusFriendId, List<KaKaoMessage> messages) {
			this.plusFriendId = plusFriendId;
			this.messages = messages;
		}

		public Builder templateCode(String templateCode) {
			this.templateCode = templateCode;
			return this;
		}

		public Builder reserveTime(String reserveTime) {
			this.reserveTime = reserveTime;
			return this;
		}

		public Builder reserveTimeZone(String reserveTimeZone) {
			this.reserveTimeZone = reserveTimeZone;
			return this;
		}

		public Builder scheduleCode(String scheduleCode) {
			this.scheduleCode = scheduleCode;
			return this;
		}

		public KakaoBody build() {
			return new KakaoBody(this);
		}
	}

	private KakaoBody(Builder builder) {
		this.plusFriendId = builder.plusFriendId;
		this.messages = builder.messages;
		this.templateCode = builder.templateCode;
		this.reserveTime = builder.reserveTime;
		this.reserveTimeZone = builder.reserveTimeZone;
		this.scheduleCode = builder.scheduleCode;
	}

	public String getPlusFriendId() {
		return plusFriendId;
	}

	public List<KaKaoMessage> getMessages() {
		return messages;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public String getReserveTime() {
		return reserveTime;
	}

	public String getReserveTimeZone() {
		return reserveTimeZone;
	}

	public String getScheduleCode() {
		return scheduleCode;
	}
}
