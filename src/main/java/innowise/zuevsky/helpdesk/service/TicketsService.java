package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.mapper.TicketMapper;
import innowise.zuevsky.helpdesk.mapper.TicketUpdateMapper;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketsService {

    private final TicketsRepository ticketsRepository;
    private final TicketMapper ticketMapper;
    private final TicketUpdateMapper ticketUpdateMapper;

    public TicketDto getTicket(Long id) {
        return ticketMapper.mapTicketInTicketDto(ticketsRepository.getById(id));
    }

    public void saveTicket(TicketSaveDto saveDto) {
        ticketsRepository.save(ticketMapper.mapTicketSaveDtoInTicket(saveDto));
    }

    public void updateTicket(TicketUpdateDto updateDto, Long id) {
        Ticket ticket = ticketsRepository.getById(id);
        ticketUpdateMapper.updateTicketFromTicketUpdateDto(updateDto, ticket);
        ticketsRepository.save(ticket);
    }
}
