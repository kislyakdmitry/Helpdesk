package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TicketMapper {

    public Ticket mapTicketSaveDtoInTicket(TicketSaveDto saveDto) {
        return Ticket.builder()
                .name(saveDto.getName())
                .description(saveDto.getDescription())
                .createdOn(LocalDate.now())
                .desiredResolutionDate(saveDto.getDesiredResolutionDate())
                .assigneeId(saveDto.getAssigneeId())
                .ownerId(saveDto.getOwnerId())
                .state(saveDto.getState())
                .categoryId(saveDto.getCategoryId())
                .urgency(saveDto.getUrgency())
                .approverId(saveDto.getApproverId())
                .build();
    }

    public TicketDto mapTicketInTicketDto(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .name(ticket.getName())
                .build();
    }
}
