package innowise.zuevsky.helpdesk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.exception.TicketOwnerNotBelongsToUserException;
import innowise.zuevsky.helpdesk.exception.TicketStateNotDoneException;
import innowise.zuevsky.helpdesk.mapper.TicketMapper;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketsServiceTest {

  @InjectMocks private TicketsService ticketsService;

  @Mock private TicketsRepository ticketsRepository;
  @Mock private TicketMapper ticketMapper;

  private static final TicketDto ticketDto = TicketUtil.createTicketDto();
  private static final Ticket ticket = TicketUtil.createTicketForTicketDto();

  @Test
  void validateTicketStateDone_shouldThrowTicketStateNotDoneException_whenTicketStatusIsNotDone() {
    // when
    when(ticketsRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
    when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

    TicketStateNotDoneException exception =
        assertThrows(
            TicketStateNotDoneException.class,
            () -> {
              ticketsService.validateTicketStateDone(TicketUtil.TICKET_ID);
            });
    // then
    assertInstanceOf(TicketStateNotDoneException.class, exception);
    assertEquals(
        String.format("Status for ticket is not DONE! ticketId:%s", TicketUtil.TICKET_ID),
        exception.getMessage());
    verify(ticketMapper).mapTicketInTicketDto(any(Ticket.class));
    verify(ticketsRepository).findById(anyLong());
  }

  @Test
  void validateTicketOwnerBelongUser_shouldThrow() {
    // when
    when(ticketsRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
    when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

    TicketOwnerNotBelongsToUserException exception =
        assertThrows(
            TicketOwnerNotBelongsToUserException.class,
            () -> {
              ticketsService.validateTicketOwnerBelongUser(
                  TicketUtil.TICKET_ID, TicketUtil.USER_ID);
            });
    // then
    assertInstanceOf(TicketOwnerNotBelongsToUserException.class, exception);
    assertEquals(
        String.format(
            "Ticket owner not belongs to user for ticket! ticketId=%s, userId:%s",
            TicketUtil.TICKET_ID, TicketUtil.USER_ID),
        exception.getMessage());
    verify(ticketMapper).mapTicketInTicketDto(any(Ticket.class));
    verify(ticketsRepository).findById(anyLong());
  }
}
