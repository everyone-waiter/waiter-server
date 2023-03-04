package handwoong.waiter.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class MessageServiceConfig {
	@Value("${ACCESSKEY}")
	private String accessKey;
	@Value("${SECRETKEY}")
	private String secretKey;
	@Value("${SERVICEID}")
	private String serviceId;

	@Bean
	public MessageService messageService() {
		return new MessageService(getAccessKey(), getSecretKey(), getServiceId());
	}
}
