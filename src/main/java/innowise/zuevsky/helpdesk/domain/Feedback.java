package innowise.zuevsky.helpdesk.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedbacks")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedbacks_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "username")
	private String userName;

	@Column(name = "rate", nullable = false)
	private Integer rate;

	@Column(name = "text")
	private String text;

	@Column(name = "created", updatable = false)
	@CreationTimestamp
	private LocalDateTime created;

	@Column(name = "ticket_id")
	private Long ticketId;
}
