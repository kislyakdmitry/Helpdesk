package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.service.TicketsService;
import innowise.zuevsky.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketsController {

    private final TicketsService ticketsService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ENGINEER')")
    @GetMapping("/{ticketId}")
    public TicketDto getTicket(@PathVariable Long ticketId) {
        return ticketsService.getTicket(ticketId);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    @GetMapping("/my/{ownerId}")
    public List<TicketDto> getMyTickets(@PathVariable Long ownerId) {
        return ticketsService.getTicketsByOwner(ownerId);
    }

    @GetMapping("/all")
    public List<TicketDto> getAllTickets() {
        User user = userService.getCurrentUser();
        return null;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/byAllEmployeesInNew")
    public List<TicketDto> getTicketsByAllEmployeesInNew() {
        return ticketsService.getTicketsByOwnersInNew(List.of(1L));
    }

    @GetMapping("/byApproverInStates")
    public List<TicketDto> getTicketsByApproverInStates() {
        return ticketsService.getTicketsByApproverInStates();
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    @PostMapping
    public void createTicket(@Valid @RequestBody TicketSaveDto saveDto) {
        ticketsService.createTicket(saveDto);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    @PutMapping("/{ticketId}")
    public void updateTicket(@Valid @RequestBody TicketUpdateDto updateDto, @PathVariable Long ticketId) {
        ticketsService.updateTicket(updateDto, ticketId);
    }
}
