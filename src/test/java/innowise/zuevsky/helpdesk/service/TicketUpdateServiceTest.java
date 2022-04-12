package innowise.zuevsky.helpdesk.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketUpdateServiceTest {

  @InjectMocks
  private TicketUpdateService ticketUpdateService;

  @Test
  void updateTicket_ShouldPass_WhenTicketUpdatedSuccessful() {

    //given
    Ticket ticket = TicketUtil.createTicketForTicketDto();
    TicketUpdateDto ticketUpdateDto = TicketUtil.createTicketUpdateDto();
    Ticket expectedUpdatedTicket = TicketUtil.createUpdatedTicket();

    //when
    Ticket actualUpdatedTicket = ticketUpdateService.updateTicket(ticketUpdateDto, ticket);

    //then
    assertThat(actualUpdatedTicket.getId()).isEqualTo(expectedUpdatedTicket.getId());
    assertThat(actualUpdatedTicket.getName()).isEqualTo(expectedUpdatedTicket.getName());
    assertThat(actualUpdatedTicket.getDesiredResolutionDate()).isEqualTo(expectedUpdatedTicket.getDesiredResolutionDate());
  }
}