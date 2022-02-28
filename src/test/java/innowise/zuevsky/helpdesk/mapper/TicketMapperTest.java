package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class TicketMapperTest {

    private final TicketMapper ticketMapper = new TicketMapper();

    @Test
    void mapTicketSaveDtoInTicketShouldMapIfNumberOfFieldsIsRight() {
        TicketSaveDto saveDto = TicketUtil.createTicketSaveDto();
        Ticket ticket = TicketUtil.createTicket();
        assertThat(ticket)
                .usingRecursiveComparison()
                .isEqualTo(ticketMapper.mapTicketSaveDtoInTicket(saveDto));
    }

    @Test
    void mapTicketInTicketDtoShouldMapIfNumberOfFieldsIsRight() {
        Ticket ticket = TicketUtil.createTicket();
        TicketDto ticketDto = TicketUtil.createTicketDto();
        assertThat(ticketDto)
                .usingRecursiveComparison()
                .isEqualTo(ticketMapper.mapTicketInTicketDto(ticket));
    }
}
