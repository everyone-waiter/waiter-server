package handwoong.waiter.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	private String name;

	private String email;

	private String password;

	private int money;

	private String phoneNumber;

	@OneToMany(mappedBy = "member")
	private final List<Waiting> waitingList = new ArrayList<>();

	@CreatedDate
	private Timestamp createdAt;

	public static Member createMember(String name, String email, String password, int money, String phoneNumber) {
		return Member.builder()
					 .name(name)
					 .email(email)
					 .password(password)
					 .money(money)
					 .phoneNumber(phoneNumber)
					 .build();
	}

	@Builder
	private Member(String name, String email, String password, int money, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.money = money;
		this.phoneNumber = phoneNumber;
	}

	public void addWaiting(Waiting waiting) {
		waitingList.add(waiting);
	}

	public void removeWaiting(Waiting waiting) {
		boolean isRemoveWaiting = waitingList.remove(waiting);
		if (!isRemoveWaiting) {
			throw new IllegalStateException("존재하지 않는 웨이팅입니다.");
		}

		waitingList.stream().filter(w -> w.getWaitingNumber() > waiting.getWaitingNumber()).forEach(Waiting::decreaseTurn);
	}
}
