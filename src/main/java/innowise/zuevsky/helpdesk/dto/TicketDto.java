package innowise.zuevsky.helpdesk.dto;

import innowise.zuevsky.helpdesk.domain.Attachment;
import innowise.zuevsky.helpdesk.domain.Comment;
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
    private String name;
    private LocalDateTime createdOn;
    private State state;
    private Integer categoryId;
    private Urgency urgency;
    private String description;
    private LocalDate desiredResolutionDate;
    private long ownerId;
    private long approverId;
    private long assigneeId;
    private List<Attachment> attachments;
    private List<Comment> comments;
}
