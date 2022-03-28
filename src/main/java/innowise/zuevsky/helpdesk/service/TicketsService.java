package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.exception.TicketNotFoundException;
import innowise.zuevsky.helpdesk.mapper.TicketMapper;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketsService {

    private final TicketsRepository ticketsRepository;
    private final TicketMapper ticketMapper;
    private final TicketUpdateService ticketUpdateService;
    private final List<State> statesOfManagerApprover = List.of(State.APPROVED, State.DECLINED,
            State.CANCELED, State.IN_PROGRESS, State.DONE);
    private final List<State> statesOfEngineerAssignee = List.of(State.IN_PROGRESS, State.DONE);

    public TicketDto getTicket(Long id) {
        return ticketMapper.mapTicketInTicketDto(ticketsRepository.findById(id).orElseThrow(() ->
                new TicketNotFoundException("Ticket doesn't exist!")));
    }

    public void createTicket(TicketSaveDto saveDto) {
        ticketsRepository.save(ticketMapper.mapTicketSaveDtoInTicket(saveDto));
    }

    public void updateTicket(TicketUpdateDto updateDto, Long id) {
        Ticket ticket = ticketsRepository.findById(id).orElseThrow(() ->
                new TicketNotFoundException("Ticket doesn't exist!"));
        ticketsRepository.save(ticketUpdateService.updateTicket(updateDto, ticket));
    }

    public Page<TicketDto> getAllTickets(User user, Pageable pageable) {
        return switch (user.getRole()) {
            case ROLE_EMPLOYEE -> getMyTickets(user, pageable);
            case ROLE_MANAGER -> getTicketsForManager(user, pageable);
            case ROLE_ENGINEER -> getTicketsForEngineer(user, pageable);
        };
    }

    public Page<TicketDto> getMyTickets(User user, Pageable pageable) {
        return ticketsRepository.findTicketsByOwnerId(user.getId(), pageable).map(ticketMapper::mapTicketInTicketDto);
    }

    public Page<TicketDto> getTicketsForManager(User user, Pageable pageable) {
        return ticketsRepository.findTicketsForManager(
                user.getId(), statesOfManagerApprover, pageable).map(ticketMapper::mapTicketInTicketDto);
    }

    public Page<TicketDto> getTicketsForEngineer(User user, Pageable pageable) {
        return ticketsRepository.findTicketsForEngineer(
                user.getId(), statesOfEngineerAssignee, pageable).map(ticketMapper::mapTicketInTicketDto);
    }
}
