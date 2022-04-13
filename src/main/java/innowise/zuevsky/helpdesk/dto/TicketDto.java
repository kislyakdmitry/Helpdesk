package innowise.zuevsky.helpdesk.dto;

import innowise.zuevsky.helpdesk.domain.enums.Category;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {
	private Long id;
	private String name;
	private LocalDateTime createdOn;
	private State state;
	private Category category;
	private Urgency urgency;
	private String description;
	private LocalDate desiredResolutionDate;
	private Long ownerId;
	private Long approverId;
	private Long assigneeId;
}
