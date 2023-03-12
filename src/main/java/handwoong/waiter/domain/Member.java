package handwoong.waiter.domain;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@CreatedDate
	private Timestamp createdAt;

	@Builder
	public Member(String phoneNumber, String name, String email, String password, int money) {
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.email = email;
		this.password = password;
		this.money = money;
	}
}
