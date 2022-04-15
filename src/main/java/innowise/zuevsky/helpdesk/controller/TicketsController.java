package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.api.ITicketsController;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.service.TicketsService;
import innowise.zuevsky.helpdesk.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketsController implements ITicketsController {

    private final TicketsService ticketsService;
    private final UsersService usersService;

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

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ENGINEER')")
    @GetMapping("/{ticketId}")
    public TicketDto getTicket(@PathVariable Long ticketId) {
        return ticketsService.getTicket(ticketId);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    @GetMapping("/my")
    public Page<TicketDto> getMyTickets(@PageableDefault(size = 5) @SortDefault(sort = "urgency",
            direction = Sort.Direction.DESC) Pageable pageable,
                                        Long id,
                                        String name,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) String desiredDate,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) Urgency[] urgencies,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) State[] states) {
        return ticketsService.getMyTickets(
                usersService.getCurrentUser(), pageable, ticketsService.getFilterParamsDto(id, name, desiredDate, urgencies, states));
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ENGINEER')")
    @GetMapping("/all")
    public Page<TicketDto> getAllTickets(@PageableDefault(value = 5) @SortDefault(sort = "urgency",
            direction = Sort.Direction.DESC) Pageable pageable,
                                         Long id,
                                         String name,
                                         @RequestParam(defaultValue = StringUtils.EMPTY) String desiredDate,
                                         @RequestParam(defaultValue = StringUtils.EMPTY) Urgency[] urgencies,
                                         @RequestParam(defaultValue = StringUtils.EMPTY) State[] states) {
        return ticketsService.getAllTickets(
                usersService.getCurrentUser(), pageable, ticketsService.getFilterParamsDto(id, name, desiredDate, urgencies, states));
    }
}
