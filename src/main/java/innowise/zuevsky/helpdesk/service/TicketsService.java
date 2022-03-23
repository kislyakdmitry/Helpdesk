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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketsService {

    private final TicketsRepository ticketsRepository;
    private final TicketMapper ticketMapper;

    private final TicketUpdateService ticketUpdateService;

    public TicketDto getTicket(Long id) {
        return ticketMapper.mapTicketInTicketDto(ticketsRepository.findById(id).orElseThrow(() ->
                new TicketNotFoundException("Ticket doesn't exist!")));
    }

    public List<TicketDto> getAllTickets(User user) {
        List<TicketDto> allTickets = new ArrayList<>();
        switch (user.getRole()) {
            case ROLE_EMPLOYEE -> allTickets.addAll(getMyTickets(user));
            case ROLE_MANAGER -> allTickets.addAll(getTicketsForManager(user));
            case ROLE_ENGINEER -> allTickets.addAll(getTicketsForEngineer(user));
        }
        return allTickets;
    }

    public List<TicketDto> getMyTickets(User user) {
        return ticketMapper.mapTicketListInTicketDtoList(
                ticketsRepository.findTicketsByOwnerId(user.getId()));
    }

    public List<TicketDto> getTicketsForManager(User user) {
        List<State> statesOfManagerApprover = List.of(State.APPROVED, State.DECLINED,
                State.CANCELED, State.IN_PROGRESS, State.DONE);
        return ticketMapper.mapTicketListInTicketDtoList(
                ticketsRepository.findTicketsForManager(user.getId(), statesOfManagerApprover));
    }

    public List<TicketDto> getTicketsForEngineer(User user) {
        List<State> statesOfEngineerAssignee = List.of(State.IN_PROGRESS, State.DONE);
        return ticketMapper.mapTicketListInTicketDtoList(
                ticketsRepository.findTicketsForEngineer(user.getId(), statesOfEngineerAssignee));
    }

    public void createTicket(TicketSaveDto saveDto) {
        ticketsRepository.save(ticketMapper.mapTicketSaveDtoInTicket(saveDto));
    }

    public void updateTicket(TicketUpdateDto updateDto, Long id) {
        Ticket ticket = ticketsRepository.findById(id).orElseThrow(() ->
                new TicketNotFoundException("Ticket doesn't exist!"));

        ticketsRepository.save(ticketUpdateService.updateTicket(updateDto, ticket));
    }
}
