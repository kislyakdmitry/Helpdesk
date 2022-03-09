package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.service.TicketsService;
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

    @PreAuthorize("hasAuthority('ticket:get')")
    @GetMapping("/{ticketId}")
    public TicketDto getTicket(@PathVariable Long ticketId) {
        return ticketsService.getTicket(ticketId);
    }

    @GetMapping("/byOwnerId/{ownerId}")
    public List<TicketDto> getTicketsByOwner(@PathVariable Long ownerId) {
        return ticketsService.getTicketsByOwnerId(ownerId);
    }

    @GetMapping
    public List<TicketDto> getTicketsByEmployeeListInStateNew() {
        return ticketsService.getTicketsByOwnerIdListInStateNew(List.of(1L));
    }

    @GetMapping("/my")
    public List<TicketDto> getTicketsByApproverInSpecificState() {
        return ticketsService.getTicketsByApproverIdInSpecificState();
    }

    @PreAuthorize("hasAuthority('ticket:post')")
    @PostMapping
    public void saveTicket(@Valid @RequestBody TicketSaveDto saveDto) {
        ticketsService.saveTicket(saveDto);
    }

    @PutMapping("/{ticketId}")
    public void updateTicket(@Valid @RequestBody TicketUpdateDto updateDto, @PathVariable Long ticketId) {
        ticketsService.updateTicket(updateDto, ticketId);
    }
}
