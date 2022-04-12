package innowise.zuevsky.helpdesk.dto;

import innowise.zuevsky.helpdesk.domain.enums.Category;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record TicketDto(
    Long id,

    String name,

    LocalDateTime createdOn,

    State state,

    Category category,

    Urgency urgency,

    String description,

    LocalDate desiredResolutionDate,

    Long ownerId,

    Long approverId,

    Long assigneeId) {}
