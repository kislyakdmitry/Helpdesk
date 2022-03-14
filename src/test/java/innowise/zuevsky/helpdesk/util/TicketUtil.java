package innowise.zuevsky.helpdesk.util;

import innowise.zuevsky.helpdesk.domain.Attachment;
import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketUtil {

    public static final String TICKET_NAME = "test name";
    public static final String TICKET_UPDATED_NAME = "updated name";
    public static final String TICKET_DESCRIPTION = "test description";
    public static final String TICKET_UPDATED_DESCRIPTION = "updated description";
    public static final LocalDate DESIRED_DATE = LocalDate.now();
    public static final LocalDate DESIRED_UPDATED_DATE = LocalDate.parse("2099-01-01");
    public static final Long OWNER_ID = 666L;
    public static final Long ASSIGNEE_ID = 666L;
    public static final Long APPROVER_ID = 666L;
    public static final State STATE = State.NEW;
    public static final Integer CATEGORY_ID = 666;
    public static final Integer UPDATED_CATEGORY_ID = 777;
    public static final Urgency URGENCY = Urgency.LOW;
    public static final Urgency UPDATED_URGENCY = Urgency.CRITICAL;
    public static final List<Attachment> ATTACHMENTS = Collections.emptyList();

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
                .attachments(ATTACHMENTS)
                .build();
    }

    public static List<TicketDto> createListOfTicketDto() {
        List<TicketDto> listOfTicketDto = new ArrayList<>();
        listOfTicketDto.add(createTicketDto());
        listOfTicketDto.add(createTicketDto());
        listOfTicketDto.add(createTicketDto());
        return listOfTicketDto;
    }

    public static TicketUpdateDto createTicketUpdateDto() {
        return TicketUpdateDto.builder()
                .name(TICKET_UPDATED_NAME)
                .description(TICKET_UPDATED_DESCRIPTION)
                .desiredResolutionDate(DESIRED_UPDATED_DATE)
                .categoryId(UPDATED_CATEGORY_ID)
                .urgency(UPDATED_URGENCY)
                .attachments(ATTACHMENTS)
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
                .build();
    }

    public static List<Ticket> createListOfTickets() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(createTicket());
        tickets.add(createTicket());
        tickets.add(createTicket());
        return tickets;
    }
}
