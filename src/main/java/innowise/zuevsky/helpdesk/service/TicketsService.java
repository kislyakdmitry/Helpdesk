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

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TicketsService {

    private final TicketsRepository ticketsRepository;
    private final TicketMapper ticketMapper;
    private final UserService userService;

    private final TicketUpdateService ticketUpdateService;

    public TicketDto getTicket(Long id) {
        return ticketMapper.mapTicketInTicketDto(ticketsRepository.findById(id).orElseThrow(() ->
                new TicketNotFoundException("Ticket doesn't exist!")));
    }

    public List<TicketDto> getTicketsByOwnerId(Long id) {
        return ticketMapper.mapTicketListInTicketDtoList(
                ticketsRepository.findTicketsByOwnerId(id));
    }

    public List<TicketDto> getTicketsByOwnerIdListInStateNew(List<Long> ownersId) {
        return ticketMapper.mapTicketListInTicketDtoList(
                ticketsRepository.getTicketsByOwnerIdListInStateNew(ownersId));
    }

    public List<TicketDto> getTicketsByApproverIdInSpecificState() {
        User user = userService.getCurrentUser();

        Set<State> targetStates = Set.of(State.APPROVED, State.DECLINED, State.IN_PROGRESS, State.CANCELED, State.DONE);

        return ticketMapper.mapTicketListInTicketDtoList(
                ticketsRepository.getTicketsByApproverIdInStates(user.getId(), targetStates));

    }

    public void saveTicket(TicketSaveDto saveDto) {
        ticketsRepository.save(ticketMapper.mapTicketSaveDtoInTicket(saveDto));
    }

    public void updateTicket(TicketUpdateDto updateDto, Long id) {
        Ticket ticket = ticketsRepository.findById(id).orElseThrow();

        ticketsRepository.save(ticketUpdateService.updateTicket(updateDto, ticket));
    }
}
