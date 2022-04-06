package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.exception.TicketNotFoundException;
import innowise.zuevsky.helpdesk.mapper.FilterParamsMapper;
import innowise.zuevsky.helpdesk.mapper.TicketMapper;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketsServiceTest {

    @Mock
    private TicketsRepository ticketsRepository;
    @Mock
    private TicketMapper ticketMapper;
    @Mock
    private TicketUpdateService ticketUpdateService;
    @Mock
    private FilterParamsMapper filterParamsMapper;

    @InjectMocks
    private TicketsService ticketsService;

    @Test
    void getTicket_ShouldPass_WhenCurrentTicketDtoIsEqualToSampleTicketDto() {

        //given
        TicketDto expectedDto = TicketUtil.createTicketDto();
        Ticket expectedTicket = TicketUtil.createTicketForTicketDto();
        when(ticketsRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(expectedTicket));
        when(ticketMapper.mapTicketInTicketDto(any(Ticket.class))).thenReturn(expectedDto);

        //when
        TicketDto currentTicketDto = ticketsService.getTicket(TicketUtil.TICKET_ID);

        //then
        assertThat(currentTicketDto).isEqualTo(expectedDto);
    }

    @Test
    void getTicket_ShouldThrowException_WhenTicketNotFound() {

        //given
        TicketDto expectedDto = TicketUtil.createTicketDto();
        Ticket expectedTicket = TicketUtil.createTicketForTicketDto();
        when(ticketsRepository.findById(any(Long.class))).thenThrow(new TicketNotFoundException(expectedTicket.getId()));

        //then
        assertThatThrownBy(() -> ticketsService.getTicket(expectedTicket.getId()))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage(String.format("Ticket doesn't exist! ID: %d", expectedTicket.getId()));
    }

    @Test
    void createTicket() {
    }

    @Test
    void updateTicket() {
    }

    @Test
    void getAllTickets() {
    }

    @Test
    void getMyTickets() {
    }

    @Test
    void getTicketsForManager() {
    }

    @Test
    void getTicketsForEngineer() {
    }

    @Test
    void getFilterParamsDto() {
    }
}