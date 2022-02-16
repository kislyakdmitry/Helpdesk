package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.service.TicketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketsController {

    private final TicketsService ticketsService;

    @GetMapping("/api/tickets")
    public TicketDto getTicket(Long id) {
        return ticketsService.getTicket(id);
    }

    @PostMapping("/api/tickets")
    public void saveTicket(@Valid @RequestBody TicketSaveDto saveDto) {
        ticketsService.saveTicket(saveDto);
    }
}
