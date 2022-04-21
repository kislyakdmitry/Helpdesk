package innowise.zuevsky.helpdesk.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.util.TicketTestUtil;
import org.junit.jupiter.api.Test;

class TicketMapperTest {

	private final TicketMapper ticketMapper = new TicketMapper();

    @Test
    void mapTicketSaveDtoInTicket_ShouldPass_IfNumberOfFieldsIsRight() {
        TicketSaveDto saveDto = TicketTestUtil.createTicketSaveDto();
        Ticket ticket = TicketTestUtil.createTicketForTicketSaveDto();

        assertThat(ticket)
                .usingRecursiveComparison()
                .isEqualTo(ticketMapper.mapTicketSaveDtoInTicket(saveDto));
    }

    @Test
    void mapTicketInTicketDto_ShouldPass_IfNumberOfFieldsIsRight() {
        Ticket ticket = TicketTestUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketTestUtil.createTicketDto();

        assertThat(ticketDto)
                .usingRecursiveComparison()
                .isEqualTo(ticketMapper.mapTicketInTicketDto(ticket));
    }
}
