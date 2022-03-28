package innowise.zuevsky.helpdesk.dto;

import innowise.zuevsky.helpdesk.domain.Attachment;
import innowise.zuevsky.helpdesk.domain.Comment;
import innowise.zuevsky.helpdesk.domain.enums.Category;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
