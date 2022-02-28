package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketMapper {

    public Ticket mapTicketSaveDtoInTicket(TicketSaveDto saveDto) {
        return Ticket.builder()
                .name(saveDto.getName())
                .description(saveDto.getDescription())
                .desiredResolutionDate(saveDto.getDesiredResolutionDate())
                .assigneeId(saveDto.getAssigneeId())
                .ownerId(saveDto.getOwnerId())
                .state(saveDto.getState())
                .categoryId(saveDto.getCategoryId())
                .urgency(saveDto.getUrgency())
                .approverId(saveDto.getApproverId())
                .attachments(saveDto.getAttachments())
                .build();
    }

    public TicketDto mapTicketInTicketDto(Ticket ticket) {
        return TicketDto.builder()
                .name(ticket.getName())
                .createdOn(ticket.getCreatedOn())
                .state(ticket.getState())
                .categoryId(ticket.getCategoryId())
                .urgency(ticket.getUrgency())
                .description(ticket.getDescription())
                .desiredResolutionDate(ticket.getDesiredResolutionDate())
                .ownerId(ticket.getOwnerId())
                .approverId(ticket.getApproverId())
                .assigneeId(ticket.getAssigneeId())
                .attachments(ticket.getAttachments())
                .comments(ticket.getComments())
                .build();
    }

    public List<TicketDto> mapTicketListInTicketDtoList(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::mapTicketInTicketDto)
                .toList();
    }
}
