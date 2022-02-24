package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.service.TicketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketsController {

    private final TicketsService ticketsService;

    @GetMapping("/{ticketId}")
    public TicketDto getTicket(@PathVariable Long ticketId) {
        return ticketsService.getTicket(ticketId);
    }

    @PostMapping
    public void saveTicket(@Valid @RequestBody TicketSaveDto saveDto) {
        ticketsService.saveTicket(saveDto);
    }

    @PutMapping("/{ticketId}")
    public void updateTicket(@Valid @RequestBody TicketUpdateDto updateDto, @PathVariable Long ticketId) {
        ticketsService.updateTicket(updateDto, ticketId);
    }
}
