package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import org.springframework.stereotype.Service;

@Service
public class TicketUpdateService {

    private TicketUpdateService() {

    }

    public static Ticket updateTicket(TicketUpdateDto updateDto, Ticket ticket) {
        ticket.setName(updateDto.getName());
        ticket.setDescription(updateDto.getDescription());
        ticket.setDesiredResolutionDate(updateDto.getDesiredResolutionDate());
        ticket.setCategory(updateDto.getCategory());
        ticket.setUrgency(updateDto.getUrgency());
        ticket.setAttachments(updateDto.getAttachments());
        return ticket;
    }
}
