package innowise.zuevsky.helpdesk.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import org.junit.jupiter.api.Test;

class TicketMapperTest {

	private final TicketMapper ticketMapper = new TicketMapper();

    @Test
    void mapTicketSaveDtoInTicket_ShouldPass_IfNumberOfFieldsIsRight() {
        TicketSaveDto saveDto = TicketUtil.createTicketSaveDto();
        Ticket ticket = TicketUtil.createTicketForTicketSaveDto();

        assertThat(ticket)
                .usingRecursiveComparison()
                .isEqualTo(ticketMapper.mapTicketSaveDtoInTicket(saveDto));
    }

    @Test
    void mapTicketInTicketDto_ShouldPass_IfNumberOfFieldsIsRight() {
        Ticket ticket = TicketUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketUtil.createTicketDto();

        assertThat(ticketDto)
                .usingRecursiveComparison()
                .isEqualTo(ticketMapper.mapTicketInTicketDto(ticket));
    }
}
