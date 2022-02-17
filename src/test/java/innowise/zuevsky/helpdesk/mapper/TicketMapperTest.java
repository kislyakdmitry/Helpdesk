package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TicketMapperTest {

    private TicketMapper ticketMapper = new TicketMapper();

    @Test
    void mapTicketSaveDtoInTicketShouldMapIfNumberOfFieldsIsRight() {
        TicketSaveDto saveDto = TicketSaveDto.builder()
                .name("test name")
                .description("test description")
                .desiredResolutionDate(LocalDate.now())
                .assigneeId(1L)
                .ownerId(1L)
                .state(State.NEW)
                .categoryId(1)
                .urgency(Urgency.LOW)
                .approverId(1L)
                .build();
        Ticket ticket = Ticket.builder()
                .id(1L)
                .name("test name")
                .description("test description")
                .createdOn(LocalDate.now())
                .desiredResolutionDate(LocalDate.now())
                .assigneeId(1L)
                .ownerId(1L)
                .state(State.NEW)
                .categoryId(1)
                .urgency(Urgency.LOW)
                .approverId(1L)
                .build();
        assertEquals(ticket.getClass(), ticketMapper.mapTicketSaveDtoInTicket(saveDto).getClass());
    }

    @Test
    void mapTicketInTicketDtoShouldMapIfNumberOfFieldsIsRight() {
        Ticket ticket = Ticket.builder()
                .id(1L)
                .name("test name")
                .description("test description")
                .createdOn(LocalDate.now())
                .desiredResolutionDate(LocalDate.now())
                .assigneeId(1L)
                .ownerId(1L)
                .state(State.NEW)
                .categoryId(1)
                .urgency(Urgency.LOW)
                .approverId(1L)
                .build();
        TicketDto ticketDto = TicketDto.builder().id(1L).name("test name").build();
        assertEquals(ticketDto, ticketMapper.mapTicketInTicketDto(ticket));
    }
}
