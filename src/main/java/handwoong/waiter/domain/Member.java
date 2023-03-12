package handwoong.waiter.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Member {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "member_id")
	private UUID id;

	private String phoneNumber;

	private String name;

	private String email;

	private String password;

	private int money;

	private LocalDateTime createdAt;

	@Builder
	public Member(UUID id, String phoneNumber, String name, String email, String password, int money, LocalDateTime createdAt) {
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.email = email;
		this.password = password;
		this.money = money;
		this.createdAt = createdAt;
	}
}
