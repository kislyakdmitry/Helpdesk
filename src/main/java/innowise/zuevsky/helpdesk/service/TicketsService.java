package innowise.zuevsky.helpdesk.service;

import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasDesiredDate;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasId;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasName;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasOwnerId;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasState;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasUrgency;
import static org.springframework.data.jpa.domain.Specification.where;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.FilterParamsDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.exception.TicketNotFoundException;
import innowise.zuevsky.helpdesk.mapper.FilterParamsMapper;
import innowise.zuevsky.helpdesk.mapper.TicketMapper;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketsService {

  private final TicketsRepository ticketsRepository;
  private final TicketMapper ticketMapper;
  private final TicketUpdateService ticketUpdateService;
  private final FilterParamsMapper filterParamsMapper;

  public TicketDto getTicket(Long id) {
    return ticketMapper.mapTicketInTicketDto(
        ticketsRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id)));
  }

  public void createTicket(TicketSaveDto saveDto) {
    ticketsRepository.save(ticketMapper.mapTicketSaveDtoInTicket(saveDto));
  }

  public void updateTicket(TicketUpdateDto updateDto, Long id) {
    Ticket ticket =
        ticketsRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
    ticketsRepository.save(ticketUpdateService.updateTicket(updateDto, ticket));
  }

  public Page<TicketDto> getAllTickets(User user, Pageable pageable, FilterParamsDto filterParams) {
    return switch (user.getRole()) {
      case ROLE_EMPLOYEE -> getMyTickets(user, pageable, filterParams);
      case ROLE_MANAGER -> getTicketsForManager(user, pageable, filterParams);
      case ROLE_ENGINEER -> getTicketsForEngineer(user, pageable, filterParams);
    };
  }

  public Page<TicketDto> getMyTickets(User user, Pageable pageable, FilterParamsDto filterParams) {

    return ticketsRepository
        .findAll(
            where(
                    hasOwnerId(user.getId())
                        .and(
                            hasUrgency(
                                filterParams.getUrgencies().isEmpty()
                                    ? List.of(Urgency.values())
                                    : filterParams.getUrgencies())))
                .and(
                    hasState(
                        filterParams.getStates().isEmpty()
                            ? List.of(State.values())
                            : filterParams.getStates()))
                .and(filterParams.getId() == null ? null : hasId(filterParams.getId()))
                .and(filterParams.getName() == null ? null : hasName(filterParams.getName()))
                .and(
                    filterParams.getDesiredDate() == null
                        ? null
                        : hasDesiredDate(filterParams.getDesiredDate())),
            pageable)
        .map(ticketMapper::mapTicketInTicketDto);
  }

  public Page<TicketDto> getTicketsForManager(
      User user, Pageable pageable, FilterParamsDto filterParams) {
    return ticketsRepository
        .findTicketsForManager(
            user.getId(),
            TicketServiceUtil.statesOfManagerApprover,
            filterParams.getId(),
            filterParams.getName(),
            filterParams.getDesiredDate(),
            filterParams.getStates().isEmpty()
                ? Arrays.stream(State.values()).toList()
                : filterParams.getStates(),
            filterParams.getUrgencies().isEmpty()
                ? Arrays.stream(Urgency.values()).toList()
                : filterParams.getUrgencies(),
            pageable)
        .map(ticketMapper::mapTicketInTicketDto);
  }

  public Page<TicketDto> getTicketsForEngineer(
      User user, Pageable pageable, FilterParamsDto filterParams) {
    return ticketsRepository
        .findTicketsForEngineer(
            user.getId(),
            TicketServiceUtil.statesOfEngineerAssignee,
            filterParams.getId(),
            filterParams.getName(),
            filterParams.getDesiredDate(),
            filterParams.getStates().isEmpty()
                ? Arrays.stream(State.values()).toList()
                : filterParams.getStates(),
            filterParams.getUrgencies().isEmpty()
                ? Arrays.stream(Urgency.values()).toList()
                : filterParams.getUrgencies(),
            pageable)
        .map(ticketMapper::mapTicketInTicketDto);
  }

  public FilterParamsDto getFilterParamsDto(
      Long id, String name, String desiredDate, Urgency[] urgencies, State[] states) {
    return filterParamsMapper.mapParamsInFilterParamsDto(id, name, desiredDate, urgencies, states);
  }

  public boolean isTicketStateDone(Long ticketId) {
    TicketDto ticketDto = getTicket(ticketId);
    if (ticketDto.getState().equals(State.DONE)) {
      return true;
    }
    return false;
  }

  public boolean isTicketOwnerBelongUser(Long ticketId, Long userId) {
    TicketDto ticketDto = getTicket(ticketId);
    if (Objects.equals(ticketDto.getOwnerId(), userId)) {
      return true;
    }
    return false;
  }
}
