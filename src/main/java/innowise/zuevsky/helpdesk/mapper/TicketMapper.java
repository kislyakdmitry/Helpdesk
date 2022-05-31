package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket mapTicketSaveDtoInTicket(TicketSaveDto saveDto) {
        return Ticket.builder().name(saveDto.getName()).description(saveDto.getDescription())
                .desiredResolutionDate(saveDto.getDesiredResolutionDate()).assigneeUserName(saveDto.getAssigneeUserName())
                .ownerUserName(saveDto.getOwnerUserName()).state(saveDto.getState()).category(saveDto.getCategory())
                .urgency(saveDto.getUrgency()).approverUserName(saveDto.getApproverUserName()).attachments(saveDto.getAttachments())
                .build();
    }

    public TicketDto mapTicketInTicketDto(Ticket ticket) {
        return TicketDto.builder().id(ticket.getId()).name(ticket.getName()).createdOn(ticket.getCreatedOn())
                .state(ticket.getState()).category(ticket.getCategory()).urgency(ticket.getUrgency())
                .description(ticket.getDescription()).desiredResolutionDate(ticket.getDesiredResolutionDate())
                .ownerId(ticket.getOwnerUserName()).approverId(ticket.getApproverUserName()).assigneeId(ticket.getAssigneeUserName())
//                .attachments(ticket.getAttachments()).comments(ticket.getComments())
                .build();
    }
}
