package handwoong.waiter.domain;

import static jakarta.persistence.FetchType.*;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	private String name;

	private String description;

	private String notice;

	@Enumerated(EnumType.STRING)
	private MenuState status;

	private String image;

	private int sort;

	@CreatedDate
	private Timestamp createdAt;

	@LastModifiedDate
	private Timestamp updatedAt;
}
