package innowise.zuevsky.helpdesk.util;

import innowise.zuevsky.helpdesk.domain.Attachment;
import innowise.zuevsky.helpdesk.domain.Comment;
import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TicketUtil {

    public static final String TICKET_NAME = "test name";
    public static final String TICKET_DESCRIPTION = "test description";
    public static final LocalDate DESIRED_DATE = LocalDate.now();
    public static final LocalDateTime CREATED_ON = LocalDateTime.now();
    public static final Long TICKET_ID = 666L;
    public static final Long OWNER_ID = 666L;
    public static final Long ASSIGNEE_ID = 666L;
    public static final Long APPROVER_ID = 666L;
    public static final State STATE = State.NEW;
    public static final Integer CATEGORY_ID = 666;
    public static final Urgency URGENCY = Urgency.LOW;
    public static final List<Attachment> ATTACHMENTS = null;
    public static final List<Comment> COMMENTS = null;

    public static TicketSaveDto createTicketSaveDto() {
        return TicketSaveDto.builder()
                .name(TICKET_NAME)
                .description(TICKET_DESCRIPTION)
                .desiredResolutionDate(DESIRED_DATE)
                .assigneeId(ASSIGNEE_ID)
                .ownerId(OWNER_ID)
                .state(STATE)
                .categoryId(CATEGORY_ID)
                .urgency(URGENCY)
                .approverId(APPROVER_ID)
                .attachments(ATTACHMENTS)
                .build();
    }

    public static TicketDto createTicketDto() {
        return TicketDto.builder()
                .name(TICKET_NAME)
                .state(STATE)
                .categoryId(CATEGORY_ID)
                .urgency(URGENCY)
                .description(TICKET_DESCRIPTION)
                .desiredResolutionDate(DESIRED_DATE)
                .ownerId(OWNER_ID)
                .approverId(APPROVER_ID)
                .assigneeId(ASSIGNEE_ID)
                .build();
    }


    public static Ticket createTicket() {
        return Ticket.builder()
                .name(TICKET_NAME)
                .description(TICKET_DESCRIPTION)
                .desiredResolutionDate(DESIRED_DATE)
                .assigneeId(ASSIGNEE_ID)
                .ownerId(OWNER_ID)
                .state(STATE)
                .categoryId(CATEGORY_ID)
                .urgency(URGENCY)
                .approverId(APPROVER_ID)
                .attachments(ATTACHMENTS)
                .comments(COMMENTS)
                .build();
    }
}
