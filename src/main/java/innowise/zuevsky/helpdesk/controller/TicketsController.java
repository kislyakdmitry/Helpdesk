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
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/helpdesk-service/tickets")
@RequiredArgsConstructor
public class TicketsController implements ITicketsController {

    private final TicketsService ticketsService;
    private final UsersService usersService;

    @PostMapping
    @RolesAllowed({ "EMPLOYEE", "MANAGER" })
    public void createTicket(@Valid @RequestBody TicketSaveDto saveDto) {
        ticketsService.createTicket(saveDto);
    }

    @PutMapping("/update/{ticketId}")
    @RolesAllowed({ "EMPLOYEE", "MANAGER" })
    public void updateTicket(@Valid @RequestBody TicketUpdateDto updateDto, @PathVariable Long ticketId) {
        ticketsService.updateTicket(updateDto, ticketId);
    }

    @GetMapping("/get/{ticketId}")
    @RolesAllowed({ "EMPLOYEE", "MANAGER", "ENGINEER" })
    public TicketDto getTicket(@PathVariable Long ticketId) {
        return ticketsService.getTicket(ticketId);
    }

    @GetMapping("/my")
    @RolesAllowed({ "EMPLOYEE", "MANAGER" })
    public Page<TicketDto> getMyTickets(@PageableDefault(size = 5) @SortDefault(sort = {"urgency", "desiredResolutionDate"},
            direction = Sort.Direction.DESC) Pageable pageable,
                                        Long id,
                                        String name,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) String desiredDate,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) Urgency[] urgencies,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) State[] states) {
        return ticketsService.getMyTickets(
                usersService.getCurrentUser().getUserName(), pageable, ticketsService.getFilterParamsDto(id, name, desiredDate, urgencies, states));
    }

    @GetMapping("/all")
    @RolesAllowed({ "EMPLOYEE", "MANAGER", "ENGINEER" })
    public Page<TicketDto> getAllTickets(@PageableDefault(value = 5) @SortDefault(sort = {"urgency", "desiredResolutionDate"},
            direction = Sort.Direction.DESC) Pageable pageable,
                                         Long id,
                                         String name,
                                         @RequestParam(defaultValue = StringUtils.EMPTY) String desiredDate,
                                         @RequestParam(defaultValue = StringUtils.EMPTY) Urgency[] urgencies,
                                         @RequestParam(defaultValue = StringUtils.EMPTY) State[] states) {
        return ticketsService.getAllTickets(
                usersService.getCurrentUser()
                , pageable, ticketsService.getFilterParamsDto(id, name, desiredDate, urgencies, states));
    }
}
