package innowise.zuevsky.helpdesk.domain;

import innowise.zuevsky.helpdesk.domain.enums.Category;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tickets_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "created_on", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdOn;

	@Column(name = "desired_resolution_date")
	private LocalDate desiredResolutionDate;

	@Column(name = "assignee_id", updatable = false)
	private Long assigneeId;

	@Column(name = "owner_id", updatable = false)
	private Long ownerId;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "state")
	private State state;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "category")
	private Category category;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "urgency")
	private Urgency urgency;

	@Column(name = "approver_id", updatable = false)
	private Long approverId;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_id")
	private List<Attachment> attachments;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_id")
	private List<Comment> comments;
}
