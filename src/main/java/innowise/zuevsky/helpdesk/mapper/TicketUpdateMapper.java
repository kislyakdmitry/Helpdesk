package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketUpdateMapper {
    void updateTicketFromTicketUpdateDto(TicketUpdateDto updateDto, @MappingTarget Ticket ticket);
}
