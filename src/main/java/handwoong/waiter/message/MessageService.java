package handwoong.waiter.message;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import handwoong.waiter.message.request.kakao.KakaoBody;
import handwoong.waiter.message.response.MessageResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageService {
	private final String accessKey;
	private final String secretKey;
	private final String serviceId;

	public MessageService(String accessKey, String secretKey, String serviceId) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.serviceId = serviceId;
	}

	public MessageResponse send(MessageType messageType, KakaoBody messageBody) {
		String sendType = messageType.name().toLowerCase();
		String url = "/" + sendType + "/v2/services/" + serviceId + "/messages";

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OkHttpClient client = new OkHttpClient();

		try {
			MessageHeader header = new MessageHeader(sendType, accessKey, secretKey, serviceId);
			Request request = createRequest(messageBody, url, mapper, header);
			Response response = client.newCall(request).execute();
			assert response.body() != null;
			return mapper.readValue(response.body().string(), MessageResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@NotNull
	private Request createRequest(KakaoBody messageBody, String url, ObjectMapper mapper, MessageHeader header) throws
		Exception {
		return header.createHeader(url)
					 .url("https://sens.apigw.ntruss.com" + url)
					 .post(RequestBody.create(mapper.writeValueAsString(messageBody), MediaType.parse("application/json")))
					 .build();
	}
}
