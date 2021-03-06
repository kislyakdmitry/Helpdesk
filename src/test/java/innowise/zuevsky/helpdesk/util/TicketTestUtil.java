package innowise.zuevsky.helpdesk.util;

import innowise.zuevsky.helpdesk.domain.Attachment;
import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.Category;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.FilterParamsDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class TicketTestUtil {

    public static final Long TICKET_ID = 69L;
    public static final String NAME = "test name";
    public static final String UPDATED_NAME = "updated name";
    public static final String DESCRIPTION = "test description";
    public static final String UPDATED_DESCRIPTION = "updated description";
    public static final LocalDate DESIRED_DATE = LocalDate.parse("2070-01-01");
    public static final LocalDate DESIRED_UPDATED_DATE = LocalDate.parse("2099-01-01");
    public static final Long OWNER_ID = 666L;
    public static final Long ASSIGNEE_ID = 666L;
    public static final Long APPROVER_ID = 666L;
    public static final State STATE = State.NEW;
    public static final Category CATEGORY = Category.BENEFITS_AND_PAPER_WORK;
    public static final Category UPDATED_CATEGORY = Category.HARDWARE_AND_SOFTWARE;
    public static final Urgency URGENCY = Urgency.LOW;
    public static final Urgency UPDATED_URGENCY = Urgency.CRITICAL;
    public static final List<Attachment> ATTACHMENTS = Collections.emptyList();
    public static final String DESIRED_DATE_FOR_FILTER_PARAMS = "2070-01-01";
    public static final Urgency[] URGENCIES_FOR_FILTER_PARAMS = {Urgency.LOW};
    public static final State[] STATES_FOR_FILTER_PARAMS = {State.NEW};

    public static TicketSaveDto createTicketSaveDto() {
        return TicketSaveDto.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .desiredResolutionDate(DESIRED_DATE)
                .assigneeId(ASSIGNEE_ID)
                .ownerId(OWNER_ID)
                .state(STATE)
                .category(CATEGORY)
                .urgency(URGENCY)
                .approverId(APPROVER_ID)
                .attachments(ATTACHMENTS)
                .build();
    }

    public static TicketDto createTicketDto() {
        return TicketDto.builder()
                .id(TICKET_ID)
                .name(NAME)
                .state(STATE)
                .category(CATEGORY)
                .urgency(URGENCY)
                .description(DESCRIPTION)
                .desiredResolutionDate(DESIRED_DATE)
                .ownerId(OWNER_ID)
                .approverId(APPROVER_ID)
                .assigneeId(ASSIGNEE_ID)
                .attachments(ATTACHMENTS)
                .build();
    }

    public static TicketUpdateDto createTicketUpdateDto() {
        return TicketUpdateDto.builder()
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .desiredResolutionDate(DESIRED_UPDATED_DATE)
                .category(UPDATED_CATEGORY)
                .urgency(UPDATED_URGENCY)
                .attachments(ATTACHMENTS)
                .build();
    }

    public static Ticket createUpdatedTicket() {
        return Ticket.builder()
            .id(TICKET_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .desiredResolutionDate(DESIRED_UPDATED_DATE)
            .assigneeId(ASSIGNEE_ID)
            .ownerId(OWNER_ID)
            .state(STATE)
            .category(UPDATED_CATEGORY)
            .urgency(UPDATED_URGENCY)
            .approverId(APPROVER_ID)
            .attachments(ATTACHMENTS)
            .build();
    }


    public static Ticket createTicketForTicketDto() {
        return Ticket.builder()
                .id(TICKET_ID)
                .name(NAME)
                .description(DESCRIPTION)
                .desiredResolutionDate(DESIRED_DATE)
                .assigneeId(ASSIGNEE_ID)
                .ownerId(OWNER_ID)
                .state(STATE)
                .category(CATEGORY)
                .urgency(URGENCY)
                .approverId(APPROVER_ID)
                .attachments(ATTACHMENTS)
                .build();
    }

    public static Ticket createTicketForTicketSaveDto() {
        return Ticket.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .desiredResolutionDate(DESIRED_DATE)
                .assigneeId(ASSIGNEE_ID)
                .ownerId(OWNER_ID)
                .state(STATE)
                .category(CATEGORY)
                .urgency(URGENCY)
                .approverId(APPROVER_ID)
                .attachments(ATTACHMENTS)
                .build();
    }

    public static Pageable  createPageable() {
        return PageRequest.of(0, 10);
    }

    public static FilterParamsDto createFilterParamsDto() {
        return FilterParamsDto.builder()
            .id(TICKET_ID)
            .name(NAME)
            .desiredDate(DESIRED_DATE)
            .urgencies(List.of(URGENCY))
            .states(List.of(STATE))
            .build();
    }

    public static FilterParamsDto createEmptyFilterParamsDto() {
        return FilterParamsDto.builder()
                .id(null)
                .name(null)
                .desiredDate(null)
                .urgencies(List.of())
                .states(List.of())
                .build();
    }
}
