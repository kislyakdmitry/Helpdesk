package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.exception.TicketOwnerNotBelongsToUserException;
import innowise.zuevsky.helpdesk.exception.TicketStateNotDoneException;
import innowise.zuevsky.helpdesk.mapper.TicketMapper;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketsServiceTest {

    @InjectMocks
    private TicketsService ticketsService;

    @Mock
    private TicketsRepository ticketsRepository;
    @Mock
    private TicketMapper ticketMapper;

    private static final TicketDto ticketDto = TicketUtil.createTicketDto();
    private static final Ticket ticket = TicketUtil.createTicketForTicketDto();

    @Test
    void validateTicketStateDone_shouldThrowTicketStateNotDoneException_whenTicketStatusIsNotDone() {
        // given
        when(ticketsRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);
        //when
        //then
        assertThatThrownBy(() -> ticketsService.validateTicketStateDone(TicketUtil.TICKET_ID))
                .isInstanceOf(TicketStateNotDoneException.class)
                .hasMessage(String.format("Status for ticket is not DONE! ticketId:%s", TicketUtil.TICKET_ID));
        verify(ticketMapper).mapTicketInTicketDto(ticket);
        verify(ticketsRepository).findById(ticket.getId());
    }

    @Test
    void validateTicketOwnerBelongUser_shouldThrow() {
        //given
        when(ticketsRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);
        //when
        //then
        assertThatThrownBy(() -> ticketsService.validateTicketOwnerBelongUser(TicketUtil.TICKET_ID, TicketUtil.USER_ID))
                .isInstanceOf(TicketOwnerNotBelongsToUserException.class)
                .hasMessage(String.format("Ticket owner not belongs to user for ticket! ticketId=%s, userId:%s", TicketUtil.TICKET_ID, TicketUtil.USER_ID));

        verify(ticketMapper).mapTicketInTicketDto(ticket);
        verify(ticketsRepository).findById(ticket.getId());
    }
}