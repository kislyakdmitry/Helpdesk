package innowise.zuevsky.helpdesk.api;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Api(tags = "Tickets")
public interface ITicketsController {

    @ApiOperation(value = "Create ticket")
    void createTicket(@Valid @RequestBody TicketSaveDto saveDto);

    @ApiOperation(value = "Update ticket")
    void updateTicket(@Valid @RequestBody TicketUpdateDto updateDto, @PathVariable Long ticketId);

    @ApiOperation(value = "Get full ticket information")
    TicketDto getTicket(@PathVariable Long ticketId);


    @ApiOperation(value = "Get all my tickets")
    Page<TicketDto> getMyTickets(@PageableDefault(size = 5) @SortDefault(sort = "urgency",
            direction = Sort.Direction.DESC) Pageable pageable,
                                        Long id,
                                        String name,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) String desiredDate,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) Urgency[] urgencies,
                                        @RequestParam(defaultValue = StringUtils.EMPTY) State[] states);


   /* @ApiOperation(value = "Get all tickets")
    Page<TicketDto> getAllTickets(@PageableDefault(value = 5) @SortDefault(sort = "urgency",
            direction = Sort.Direction.DESC) Pageable pageable,
                                         Long id,
                                         String name,
                                         @RequestParam(defaultValue = StringUtils.EMPTY) String desiredDate,
                                         @RequestParam(defaultValue = StringUtils.EMPTY) Urgency[] urgencies,
                                         @RequestParam(defaultValue = StringUtils.EMPTY) State[] states);
*/
}
