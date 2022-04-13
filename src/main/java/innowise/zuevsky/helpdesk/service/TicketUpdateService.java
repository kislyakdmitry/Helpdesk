package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class TicketUpdateService {

  public Ticket updateTicket(TicketUpdateDto updateDto, Ticket ticket) {
    ticket.setName(updateDto.getName());
    ticket.setDescription(updateDto.getDescription());
    ticket.setDesiredResolutionDate(updateDto.getDesiredResolutionDate());
    ticket.setCategory(updateDto.getCategory());
    ticket.setUrgency(updateDto.getUrgency());
    ticket.setAttachments(updateDto.getAttachments());
    return ticket;
  }
}
