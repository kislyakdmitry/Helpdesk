package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.Role;
import innowise.zuevsky.helpdesk.dto.*;
import innowise.zuevsky.helpdesk.exception.TicketNotFoundException;
import innowise.zuevsky.helpdesk.exception.TicketOwnerNotBelongsToUserException;
import innowise.zuevsky.helpdesk.exception.TicketStateNotDoneException;
import innowise.zuevsky.helpdesk.mapper.FilterParamsMapper;
import innowise.zuevsky.helpdesk.mapper.TicketMapper;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import innowise.zuevsky.helpdesk.util.TicketTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketsServiceTest {

    @Mock
    private TicketsRepository ticketsRepository;
    @Mock
    private TicketMapper ticketMapper;
    @Mock
    private TicketUpdateService ticketUpdateService;
    @Mock
    private FilterParamsMapper filterParamsMapper;

    @InjectMocks
    private TicketsService ticketsService;

    private static final TicketDto ticketDto = TicketTestUtil.createTicketDto();
    private static final Ticket ticket = TicketTestUtil.createTicketForTicketDto();
    private static final CurrentUser user = CurrentUser.builder().userName(TicketTestUtil.OWNER_NAME).role(TicketTestUtil.OWNER_ROLE).build();

    @Test
    void getTicket_ShouldPass_WhenTicketExist() {

        //given
        TicketDto expectedDto = TicketTestUtil.createTicketDto();
        Ticket expectedTicket = TicketTestUtil.createTicketForTicketDto();

        when(ticketsRepository.findById(TicketTestUtil.TICKET_ID)).thenReturn(Optional.ofNullable(expectedTicket));
        assert expectedTicket != null;
        when(ticketMapper.mapTicketInTicketDto(expectedTicket)).thenReturn(expectedDto);

        //when
        TicketDto currentTicketDto = ticketsService.getTicket(TicketTestUtil.TICKET_ID);

        //then
        assertThat(currentTicketDto).isEqualTo(expectedDto);
    }

    @Test
    void getTicket_ShouldThrowException_WhenTicketNotFound() {

        //given
        Ticket expectedTicket = TicketTestUtil.createTicketForTicketDto();

        when(ticketsRepository.findById(expectedTicket.getId())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> ticketsService.getTicket(expectedTicket.getId()))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage(String.format("Ticket doesn't exist! ID: %d", expectedTicket.getId()));
    }

    @Test
    void createTicket_ShouldPass_WhenTicketSaved() {

        //given
        TicketSaveDto saveDto = TicketTestUtil.createTicketSaveDto();
        Ticket expectedTicket = TicketTestUtil.createTicketForTicketSaveDto();

        when(ticketMapper.mapTicketSaveDtoInTicket(saveDto)).thenReturn(expectedTicket);
        when(ticketsRepository.save(expectedTicket)).thenReturn(expectedTicket);

        //when
        ticketsService.createTicket(saveDto);

        //then
        verify(ticketMapper).mapTicketSaveDtoInTicket(saveDto);
        verify(ticketsRepository).save(expectedTicket);
    }

    @Test
    void updateTicket_ShouldPass_WhenTicketUpdated() {

        //given
        Ticket ticket = TicketTestUtil.createTicketForTicketDto();
        TicketUpdateDto ticketUpdateDto = TicketTestUtil.createTicketUpdateDto();
        Ticket expectedUpdatedTicket = TicketTestUtil.createUpdatedTicket();

        when(ticketsRepository.findById(TicketTestUtil.TICKET_ID)).thenReturn(Optional.ofNullable(ticket));
        assert ticket != null;
        when(ticketUpdateService.updateTicket(ticketUpdateDto, ticket)).thenReturn(expectedUpdatedTicket);

        //when
        ticketsService.updateTicket(ticketUpdateDto, TicketTestUtil.TICKET_ID);

        //then
        verify(ticketsRepository).findById(TicketTestUtil.TICKET_ID);
        verify(ticketUpdateService).updateTicket(ticketUpdateDto, ticket);
        verify(ticketsRepository).save(expectedUpdatedTicket);
    }

    @Test
    void updateTicket_ShouldThrowException_WhenTicketNotFoundWhileUpdating() {

        //given
        TicketUpdateDto ticketUpdateDto = TicketTestUtil.createTicketUpdateDto();

        when(ticketsRepository.findById(TicketTestUtil.TICKET_ID)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> ticketsService.updateTicket(ticketUpdateDto, TicketTestUtil.TICKET_ID))
            .isInstanceOf(TicketNotFoundException.class)
            .hasMessage(String.format("Ticket doesn't exist! ID: %d", TicketTestUtil.TICKET_ID));

    }

    @Test
    void getAllTickets_ShouldPass_WhenActualPageOfTicketsDtoIsValid_ForRoleEmployee() {

        //given
        CurrentUser user = CurrentUser.builder().userName("user1_mogilev").role(Role.ROLE_EMPLOYEE).build();
        Pageable pageable = TicketTestUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketTestUtil.createFilterParamsDto();
        Ticket ticket = TicketTestUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketTestUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));
        when(ticketsRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getAllTickets(user, pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getAllTickets_ShouldPass_WhenActualPageOfTicketsDtoIsValid_ForRoleManager() {

        //given
        CurrentUser user = CurrentUser.builder().userName("manager1_mogilev").role(Role.ROLE_MANAGER).build();
        Pageable pageable = TicketTestUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketTestUtil.createFilterParamsDto();
        Ticket ticket = TicketTestUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketTestUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findTicketsForManager(
            user.getUserName(), TicketServiceUtil.statesOfManagerApprover, filterParamsDto.getId(),
            filterParamsDto.getName(), filterParamsDto.getDesiredDate(), filterParamsDto.getStates(),
            filterParamsDto.getUrgencies(), pageable))
            .thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getAllTickets(user, pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getAllTickets_ShouldPass_WhenActualPageOfTicketsDtoIsValid_ForRoleEngineer() {

        //given
        CurrentUser user = CurrentUser.builder().userName("engineer1_mogilev").role(Role.ROLE_EMPLOYEE).build();
        Pageable pageable = TicketTestUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketTestUtil.createFilterParamsDto();
        Ticket ticket = TicketTestUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketTestUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findTicketsForEngineer(
            user.getUserName(), TicketServiceUtil.statesOfEngineerAssignee, filterParamsDto.getId(),
            filterParamsDto.getName(), filterParamsDto.getDesiredDate(), filterParamsDto.getStates(),
            filterParamsDto.getUrgencies(), pageable))
            .thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getTicketsForEngineer(user.getUserName(), pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getMyTickets_ShouldPass_WhenActualPageOfTicketsDtoIsValid() {

        //given
        CurrentUser user = CurrentUser.builder().userName("user1_mogilev").role(Role.ROLE_EMPLOYEE).build();
        Pageable pageable = TicketTestUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketTestUtil.createFilterParamsDto();
        Ticket ticket = TicketTestUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketTestUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getMyTickets(user.getUserName(), pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getTicketsForManager_ShouldPass_WhenActualPageOfTicketsDtoIsValid() {

        //given
        CurrentUser user = CurrentUser.builder().userName("manager1_mogilev").role(Role.ROLE_MANAGER).build();
        Pageable pageable = TicketTestUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketTestUtil.createFilterParamsDto();
        Ticket ticket = TicketTestUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketTestUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findTicketsForManager(
            user.getUserName(), TicketServiceUtil.statesOfManagerApprover, filterParamsDto.getId(),
            filterParamsDto.getName(), filterParamsDto.getDesiredDate(), filterParamsDto.getStates(),
            filterParamsDto.getUrgencies(), pageable))
            .thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getTicketsForManager(user.getUserName(), pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getTicketsForEngineer_ShouldPass_WhenActualPageOfTicketsDtoIsValid() {

        //given
        CurrentUser user = CurrentUser.builder().userName("engineer1_mogilev").role(Role.ROLE_ENGINEER).build();
        Pageable pageable = TicketTestUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketTestUtil.createFilterParamsDto();
        Ticket ticket = TicketTestUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketTestUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findTicketsForEngineer(
            user.getUserName(), TicketServiceUtil.statesOfEngineerAssignee, filterParamsDto.getId(),
            filterParamsDto.getName(), filterParamsDto.getDesiredDate(), filterParamsDto.getStates(),
            filterParamsDto.getUrgencies(), pageable))
            .thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getTicketsForEngineer(user.getUserName(), pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getFilterParamsDto_ShouldPass_WhenActualFilterParamsDtoIsValid() {

        //given
        FilterParamsDto expectedFilterParamsDto = TicketTestUtil.createFilterParamsDto();

        when(filterParamsMapper.mapParamsInFilterParamsDto(
                TicketTestUtil.TICKET_ID, TicketTestUtil.NAME, TicketTestUtil.DESIRED_DATE_FOR_FILTER_PARAMS,
                TicketTestUtil.URGENCIES_FOR_FILTER_PARAMS, TicketTestUtil.STATES_FOR_FILTER_PARAMS))
                .thenReturn(expectedFilterParamsDto);

        //when
        FilterParamsDto actualFilterParamsDto = ticketsService.getFilterParamsDto(
                TicketTestUtil.TICKET_ID, TicketTestUtil.NAME, TicketTestUtil.DESIRED_DATE_FOR_FILTER_PARAMS,
                TicketTestUtil.URGENCIES_FOR_FILTER_PARAMS, TicketTestUtil.STATES_FOR_FILTER_PARAMS);

        //then
        assertThat(actualFilterParamsDto).isEqualTo(expectedFilterParamsDto);
    }

    @Test
    void validateTicketStateDone_shouldThrowTicketStateNotDoneException_whenTicketStatusIsNotDone() {
        // given
        when(ticketsRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);
        //when
        //then
        assertThatThrownBy(() -> ticketsService.validateTicketStateDone(TicketTestUtil.TICKET_ID))
                .isInstanceOf(TicketStateNotDoneException.class)
                .hasMessage(String.format("Status for ticket is not DONE! ticketId:%s", TicketTestUtil.TICKET_ID));
        verify(ticketMapper).mapTicketInTicketDto(ticket);
        verify(ticketsRepository).findById(ticket.getId());
    }

    @Test
    void validateTicketOwnerBelongUser_shouldThrowTicketOwnerNotBelongsToUserException() {
        //given
        String differentUserName = "differentUserName";

        when(ticketsRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);
        //when
        //then
        assertThatThrownBy(() -> ticketsService.validateTicketOwnerBelongUser(TicketTestUtil.TICKET_ID, "differentUserName"))
                .isInstanceOf(TicketOwnerNotBelongsToUserException.class)
                .hasMessage(String.format("Ticket owner not belongs to user for ticket! ticketId=%s, userName:%s", TicketTestUtil.TICKET_ID, differentUserName));

        verify(ticketMapper).mapTicketInTicketDto(ticket);
        verify(ticketsRepository).findById(ticket.getId());
    }

}