package innowise.zuevsky.helpdesk.service;

import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasDesiredDate;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasId;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasName;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasOwnerId;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasState;
import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasUrgency;
import static innowise.zuevsky.helpdesk.util.Validators.validateThat;
import static org.springframework.data.jpa.domain.Specification.where;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.*;
import innowise.zuevsky.helpdesk.exception.TicketNotFoundException;
import innowise.zuevsky.helpdesk.exception.TicketOwnerNotBelongsToUserException;
import innowise.zuevsky.helpdesk.exception.TicketStateNotDoneException;
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
    return ticketMapper.mapTicketInTicketDto(ticketsRepository.findById(id)
        .orElseThrow(() -> new TicketNotFoundException(id)));
  }

  public void createTicket(TicketSaveDto saveDto) {
    ticketsRepository.save(ticketMapper.mapTicketSaveDtoInTicket(saveDto));
  }

  public void updateTicket(TicketUpdateDto updateDto, Long id) {
    Ticket ticket = ticketsRepository.findById(id).orElseThrow(() ->
            new TicketNotFoundException(id));
    ticketsRepository.save(ticketUpdateService.updateTicket(updateDto, ticket));
  }
/*
  public Page<TicketDto> getAllTickets(CurrentUser user, Pageable pageable, FilterParamsDto filterParams) {
    return switch (user.getRole()) {
      case EMPLOYEE -> getMyTickets(user.getUserName(), pageable, filterParams);
      case MANAGER -> getTicketsForManager(user.getUserName(), pageable, filterParams);
      case ENGINEER -> getTicketsForEngineer(user.getUserName(), pageable, filterParams);
    };
  }*/

  public Page<TicketDto> getMyTickets(String userName, Pageable pageable, FilterParamsDto filterParams) {

    return ticketsRepository.findAll(
                    where(hasOwnerId(userName)
                            .and(hasUrgency(filterParams.getUrgencies().isEmpty() ? List.of(Urgency.values())
                                    : filterParams.getUrgencies())))
                            .and(hasState(filterParams.getStates().isEmpty() ? List.of(State.values())
                                    : filterParams.getStates()))
                            .and(filterParams.getId() == null ? null : hasId(filterParams.getId()))
                            .and(filterParams.getName() == null ? null : hasName(filterParams.getName()))
                            .and(filterParams.getDesiredDate() == null ? null
                                    : hasDesiredDate(filterParams.getDesiredDate())),
                    pageable)
            .map(ticketMapper::mapTicketInTicketDto);
  }

/*
  public Page<TicketDto> getTicketsForManager(String userName, Pageable pageable,
      FilterParamsDto filterParams) {
    return ticketsRepository.findTicketsForManager(
            userName,
            TicketServiceUtil.statesOfManagerApprover,
            filterParams.getId(),
            filterParams.getName(),
            filterParams.getDesiredDate(),
            filterParams.getStates().isEmpty() ? Arrays.stream(State.values()).toList()
                : filterParams.getStates(),
            filterParams.getUrgencies().isEmpty() ? Arrays.stream(Urgency.values()).toList()
                : filterParams.getUrgencies(),
            pageable)
        .map(ticketMapper::mapTicketInTicketDto);
  }

  public Page<TicketDto> getTicketsForEngineer(String userId, Pageable pageable,
      FilterParamsDto filterParams) {
    return ticketsRepository.findTicketsForEngineer(
            userId,
            TicketServiceUtil.statesOfEngineerAssignee,
            filterParams.getId(),
            filterParams.getName(),
            filterParams.getDesiredDate(),
            filterParams.getStates().isEmpty() ? Arrays.stream(State.values()).toList()
                : filterParams.getStates(),
            filterParams.getUrgencies().isEmpty() ? Arrays.stream(Urgency.values()).toList()
                : filterParams.getUrgencies(),
            pageable)
        .map(ticketMapper::mapTicketInTicketDto);
  }

  */
  public FilterParamsDto getFilterParamsDto(Long id, String name, String desiredDate,
      Urgency[] urgencies, State[] states) {
    return filterParamsMapper.mapParamsInFilterParamsDto(id, name, desiredDate, urgencies, states);
  }

  public void validateTicketStateDone(Long ticketId) {
    TicketDto ticketDto = getTicket(ticketId);
    validateThat(State.DONE.equals(ticketDto.getState()), () -> new TicketStateNotDoneException(ticketId));
  }

  public void validateTicketOwnerBelongUser(Long ticketId, String userId) {
    TicketDto ticketDto = getTicket(ticketId);
    validateThat(Objects.equals(ticketDto.getOwnerId(), userId),
                 () -> new TicketOwnerNotBelongsToUserException(ticketId, userId));
  }
}
