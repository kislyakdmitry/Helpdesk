package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.service.TicketsService;
import innowise.zuevsky.helpdesk.service.UsersService;
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

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketsController {

    private final TicketsService ticketsService;
    private final UsersService usersService;

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ENGINEER')")
    @GetMapping("/{ticketId}")
    public TicketDto getTicket(@PathVariable Long ticketId) {
        return ticketsService.getTicket(ticketId);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    @GetMapping("/my")
    public List<TicketDto> getMyTickets() {
        return ticketsService.getMyTickets(usersService.getCurrentUser());
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ENGINEER')")
    @GetMapping("/all")
    public List<TicketDto> getAllTickets() {
        return ticketsService.getAllTickets(usersService.getCurrentUser());
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
