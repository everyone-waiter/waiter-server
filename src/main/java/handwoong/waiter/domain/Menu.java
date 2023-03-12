package handwoong.waiter.domain;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
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

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
