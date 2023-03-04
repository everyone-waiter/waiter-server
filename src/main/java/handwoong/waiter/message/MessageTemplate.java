package handwoong.waiter.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import handwoong.waiter.customer.Customer;
import handwoong.waiter.message.request.MessageBody;
import handwoong.waiter.message.request.kakao.KakaoBody;
import handwoong.waiter.message.request.kakao.messages.ButtonType;
import handwoong.waiter.message.request.kakao.messages.KaKaoMessage;
import handwoong.waiter.message.request.kakao.messages.KakaoButton;

public class MessageTemplate {
	private final Map<TemplateType, MessageBody> adaptor = new HashMap<>();

	public MessageTemplate(Customer customer) {
		adaptor.put(TemplateType.REGISTER, getRegisterMessage(customer));
		adaptor.put(TemplateType.ENTER, getEnterMessage(customer));
		adaptor.put(TemplateType.READY, getReadyMessage(customer));
		adaptor.put(TemplateType.CANCEL, getCancelMessage(customer));
	}

	public MessageBody createTemplate(TemplateType type) {
		return adaptor.get(type);
	}

	private KakaoBody getRegisterMessage(Customer customer) {
		List<KakaoButton> buttonList = new ArrayList<>();
		buttonList.add(createMenuInfoButton());
		buttonList.add(createCheckTurn(customer));
		buttonList.add(createCancelButton(customer));
		List<KaKaoMessage> registerBodyMessage = createRegisterBodyMessage(customer, buttonList);
		return new KakaoBody.Builder("@narurestaurant", registerBodyMessage)
			.templateCode("waitingRegister")
			.build();
	}

	private KakaoBody getEnterMessage(Customer customer) {
		List<KakaoButton> buttonList = new ArrayList<>();
		buttonList.add(createCancelButton(customer));
		List<KaKaoMessage> enterBodyMessage = createEnterBodyMessage(customer, buttonList);
		return new KakaoBody.Builder("@narurestaurant", enterBodyMessage)
			.templateCode("CustomerEnter")
			.build();
	}

	private KakaoBody getReadyMessage(Customer customer) {
		List<KakaoButton> buttonList = new ArrayList<>();
		buttonList.add(createCancelButton(customer));
		List<KaKaoMessage> readyBodyMessage = createReadyBodyMessage(customer, buttonList);
		return new KakaoBody.Builder("@narurestaurant", readyBodyMessage)
			.templateCode("waitingReady")
			.build();
	}

	private KakaoBody getCancelMessage(Customer customer) {
		List<KaKaoMessage> cancelMessage = createCancelBodyMessage(customer);
		return new KakaoBody.Builder("@narurestaurant", cancelMessage)
			.templateCode("waitingCancel")
			.build();
	}

	private List<KaKaoMessage> createRegisterBodyMessage(Customer customer, List<KakaoButton> buttonList) {
		List<KaKaoMessage> messages = new ArrayList<>();
		KaKaoMessage message = new KaKaoMessage.Builder(customer.getPhoneNumber(), "나루 coffee & restaurant을 찾아주셔서 감사합니다.\n"
			+ "\n"
			+ "고객님께서는 대기 명단에 정상적으로 접수 되셨습니다.\n"
			+ "\n"
			+ "■ 인원\n"
			+ "- 성인 " + customer.getAdult() + "명\n"
			+ "- 아동 " + customer.getChildren() + "명\n"
			+ "■ 대기번호 : " + customer.getWaitingNumber() + "번\n"
			+ "■ 내 앞 대기팀 : " + customer.getWaitingTurn() + "팀\n"
			+ "■ 매장 전화번호 : 055-536-5373\n"
			+ "\n"
			+ "찾아주신 분들께 맛있고 더 나은 서비스를 제공하기 위해 노력하겠습니다.")
			.buttons(buttonList)
			.build();
		messages.add(message);
		return messages;
	}

	private List<KaKaoMessage> createEnterBodyMessage(Customer customer, List<KakaoButton> buttons) {
		List<KaKaoMessage> messages = new ArrayList<>();
		KaKaoMessage message = new KaKaoMessage.Builder(customer.getPhoneNumber(),
			"안녕하세요.\n"
				+ "나루 coffee & restaurant입니다.\n"
				+ "\n"
				+ "대기번호 " + customer.getWaitingNumber() + "번 고객님 지금 입장 해 주세요.\n"
				+ "\n"
				+ "■ 5분 이내 미 입장 시 대기 접수가 자동 취소되며, 다시 대기 등록을 해주셔야 합니다.")
			.buttons(buttons)
			.build();
		messages.add(message);
		return messages;
	}

	private List<KaKaoMessage> createReadyBodyMessage(Customer customer, List<KakaoButton> buttons) {
		List<KaKaoMessage> messages = new ArrayList<>();
		KaKaoMessage message = new KaKaoMessage.Builder(customer.getPhoneNumber(), "안녕하세요.\n"
			+ "나루 coffee & restaurant입니다.\n"
			+ "\n"
			+ "고객님의 입장까지 2팀 남았습니다.\n"
			+ "매장 근처에서 대기 해 주세요.\n"
			+ "\n"
			+ "■ 인원\n"
			+ "- 성인 " + customer.getAdult() + "명\n"
			+ "- 아동 " + customer.getChildren() + "명\n"
			+ "■ 대기번호 : " + customer.getWaitingNumber() + "번\n"
			+ "■ 내 앞 대기팀 : " + customer.getWaitingTurn() + "팀\n"
			+ "■ 매장 전화번호 : 055-536-5373\n"
			+ "\n"
			+ "찾아주신 분들께 맛있고 더 나은 서비스를 제공하기 위해 노력하겠습니다.")
			.buttons(buttons)
			.build();
		messages.add(message);
		return messages;
	}

	private List<KaKaoMessage> createCancelBodyMessage(Customer customer) {
		List<KaKaoMessage> messages = new ArrayList<>();
		KaKaoMessage message = new KaKaoMessage.Builder(customer.getPhoneNumber(), "안녕하세요.\n"
			+ "나루 coffee & restaurant입니다.\n"
			+ "\n"
			+ "대기번호 " + customer.getWaitingNumber() + "번 고객님 대기 취소가 완료되었습니다.\n"
			+ "\n"
			+ "오늘도 좋은 하루 보내세요.")
			.build();
		messages.add(message);
		return messages;
	}

	private KakaoButton createMenuInfoButton() {
		return new KakaoButton.Builder(ButtonType.WL, "메뉴 미리보기")
			.linkMobile("http://home.handwoong.com:3000/menus/stake/unused/1")
			.linkPc("http://home.handwoong.com:3000/menus/stake/unused/1")
			.build();
	}

	private KakaoButton createCheckTurn(Customer customer) {
		return new KakaoButton.Builder(ButtonType.WL, "내 순서 확인하기")
			.linkMobile("http://localhost:8080/customers/waiting/turn/" + customer.getId())
			.linkPc("http://localhost:8080/customers/waiting/turn/" + customer.getId())
			.build();
	}

	private KakaoButton createCancelButton(Customer customer) {
		return new KakaoButton.Builder(ButtonType.WL, "대기 취소하기")
			.linkMobile("http://localhost:8080/customers/waiting/cancel/" + customer.getId())
			.linkPc("http://localhost:8080/customers/waiting/cancel/" + customer.getId())
			.build();
	}
}
