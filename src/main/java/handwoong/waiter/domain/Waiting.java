package handwoong.waiter.domain;

import static jakarta.persistence.FetchType.*;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Waiting {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "waiting_id")
	private UUID id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private Long waitingNumber;

	private Long waitingTurn;

	private int adult;

	private int children;

	private String phoneNumber;

	@CreatedDate
	private Timestamp createdAt;

	public Waiting createWaiting(Member member, Long waitingNumber, Long waitingTurn, int adult, int children, String phoneNumber) {
		return Waiting.builder()
					  .member(member)
					  .waitingNumber(waitingNumber)
					  .waitingTurn(waitingTurn)
					  .adult(adult)
					  .children(children)
					  .phoneNumber(phoneNumber)
					  .build();
	}

	@Builder
	private Waiting(Member member, Long waitingNumber, Long waitingTurn, int adult, int children, String phoneNumber) {
		this.member = member;
		this.waitingNumber = waitingNumber;
		this.waitingTurn = waitingTurn;
		this.adult = adult;
		this.children = children;
		this.phoneNumber = phoneNumber;
	}
}
