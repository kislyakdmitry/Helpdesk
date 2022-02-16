package innowise.zuevsky.helpdesk.dto;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketSaveDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate desiredResolutionDate;
    private Long assigneeId;
    private Long ownerId;
    private State state;
    private int categoryId;
    private Urgency urgency;
    private Long approverId;
}
