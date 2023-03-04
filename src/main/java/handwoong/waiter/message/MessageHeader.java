package handwoong.waiter.message;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import okhttp3.Request;

public class MessageHeader {
	private final String sendType;
	private final String accessKey;
	private final String secretKey;
	private final String serviceId;

	public MessageHeader(String sendType, String accessKey, String secretKey, String serviceId) {
		this.sendType = sendType;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.serviceId = serviceId;
	}

	public Request.Builder createHeader(String url) throws Exception {
		String timestamp = Long.toString(System.currentTimeMillis());
		Request.Builder request = new Request.Builder();
		request.addHeader("Content-type", "application/json");
		request.addHeader("x-ncp-apigw-timestamp", timestamp);
		request.addHeader("x-ncp-iam-access-key", accessKey);
		request.addHeader("x-ncp-apigw-signature-v2", makeSignature(timestamp, url));
		return request;
	}

	public String makeSignature(String timestamp, String url) throws Exception {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

		return encodeBase64String;
	}
}
