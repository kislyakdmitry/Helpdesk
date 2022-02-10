package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketsService {

    private final TicketsRepository ticketsRepository;

    public TicketsService(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    public List<TicketDto> getTickets() {
        var tickets = ticketsRepository.findAll();
        return tickets.stream()
                .map(t -> TicketDto.builder()
                        .id(t.getId())
                        .name(t.getName())
                        .build())
                .toList();
    }
}
