package innowise.zuevsky.helpdesk.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.domain.enums.Role;
import innowise.zuevsky.helpdesk.dto.FilterParamsDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.exception.TicketNotFoundException;
import innowise.zuevsky.helpdesk.mapper.FilterParamsMapper;
import innowise.zuevsky.helpdesk.mapper.TicketMapper;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import innowise.zuevsky.helpdesk.util.UserUtil;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

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

    @Test
    void getTicket_ShouldPass_WhenCurrentTicketDtoIsEqualToSampleTicketDto() {

        //given
        TicketDto expectedDto = TicketUtil.createTicketDto();
        Ticket expectedTicket = TicketUtil.createTicketForTicketDto();

        when(ticketsRepository.findById(TicketUtil.ID)).thenReturn(Optional.ofNullable(expectedTicket));
        assert expectedTicket != null;
        when(ticketMapper.mapTicketInTicketDto(expectedTicket)).thenReturn(expectedDto);

        //when
        TicketDto currentTicketDto = ticketsService.getTicket(TicketUtil.ID);

        //then
        assertThat(currentTicketDto).isEqualTo(expectedDto);
    }

    @Test
    void getTicket_ShouldThrowException_WhenTicketNotFound() {

        //given
        Ticket expectedTicket = TicketUtil.createTicketForTicketDto();

        when(ticketsRepository.findById(expectedTicket.getId())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> ticketsService.getTicket(expectedTicket.getId()))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage(String.format("Ticket doesn't exist! ID: %d", expectedTicket.getId()));
    }

    @Test
    void createTicket_ShouldPass_WhenTicketSaved() {

        //given
        TicketSaveDto saveDto = TicketUtil.createTicketSaveDto();
        Ticket expectedTicket = TicketUtil.createTicketForTicketSaveDto();

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
        Ticket ticket = TicketUtil.createTicketForTicketDto();
        TicketUpdateDto ticketUpdateDto = TicketUtil.createTicketUpdateDto();
        Ticket expectedUpdatedTicket = TicketUtil.createUpdatedTicket();

        when(ticketsRepository.findById(TicketUtil.ID)).thenReturn(Optional.ofNullable(ticket));
        assert ticket != null;
        when(ticketUpdateService.updateTicket(ticketUpdateDto, ticket)).thenReturn(expectedUpdatedTicket);

        //when
        ticketsService.updateTicket(ticketUpdateDto, TicketUtil.ID);

        //then
        verify(ticketsRepository).findById(TicketUtil.ID);
        verify(ticketUpdateService).updateTicket(ticketUpdateDto, ticket);
        verify(ticketsRepository).save(expectedUpdatedTicket);
    }

    @Test
    void updateTicket_ShouldThrowException_WhenTicketNotFoundWhileUpdating() {

        //given
        TicketUpdateDto ticketUpdateDto = TicketUtil.createTicketUpdateDto();

        when(ticketsRepository.findById(TicketUtil.ID)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> ticketsService.updateTicket(ticketUpdateDto, TicketUtil.ID))
            .isInstanceOf(TicketNotFoundException.class)
            .hasMessage(String.format("Ticket doesn't exist! ID: %d", TicketUtil.ID));

    }

    @Test
    void getAllTickets_ShouldPass_WhenActualPageOfTicketsDtoIsEqualToExpectedPageOfTicketsDto_ForRoleEmployee() {

        //given
        User user = UserUtil.createTestUser();
        Pageable pageable = TicketUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketUtil.createFilterParamsDto();
        Ticket ticket = TicketUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketUtil.createTicketDto();
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
    void getAllTickets_ShouldPass_WhenActualPageOfTicketsDtoIsEqualToExpectedPageOfTicketsDto_ForRoleManager() {

        //given
        User user = UserUtil.createTestUser();
        user.setRole(Role.ROLE_MANAGER);
        Pageable pageable = TicketUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketUtil.createFilterParamsDto();
        Ticket ticket = TicketUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findTicketsForManager(
            user.getId(), TicketServiceUtil.statesOfManagerApprover, filterParamsDto.getId(),
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
    void getAllTickets_ShouldPass_WhenActualPageOfTicketsDtoIsEqualToExpectedPageOfTicketsDto_ForRoleEngineer() {

        //given
        User user = UserUtil.createTestUser();
        user.setRole(Role.ROLE_ENGINEER);
        Pageable pageable = TicketUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketUtil.createFilterParamsDto();
        Ticket ticket = TicketUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findTicketsForEngineer(
            user.getId(), TicketServiceUtil.statesOfEngineerAssignee, filterParamsDto.getId(),
            filterParamsDto.getName(), filterParamsDto.getDesiredDate(), filterParamsDto.getStates(),
            filterParamsDto.getUrgencies(), pageable))
            .thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getTicketsForEngineer(user, pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getMyTickets_ShouldPass_WhenActualPageOfTicketsDtoIsEqualToExpectedPageOfTicketsDto() {

        //given
        User user = UserUtil.createTestUser();
        Pageable pageable = TicketUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketUtil.createFilterParamsDto();
        Ticket ticket = TicketUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getMyTickets(user, pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getTicketsForManager_ShouldPass_WhenActualPageOfTicketsDtoIsEqualToExpectedPageOfTicketsDto() {

        //given
        User user = UserUtil.createTestUser();
        Pageable pageable = TicketUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketUtil.createFilterParamsDto();
        Ticket ticket = TicketUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findTicketsForManager(
            user.getId(), TicketServiceUtil.statesOfManagerApprover, filterParamsDto.getId(),
            filterParamsDto.getName(), filterParamsDto.getDesiredDate(), filterParamsDto.getStates(),
            filterParamsDto.getUrgencies(), pageable))
            .thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getTicketsForManager(user, pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getTicketsForEngineer_ShouldPass_WhenActualPageOfTicketsDtoIsEqualToExpectedPageOfTicketsDto() {

        //given
        User user = UserUtil.createTestUser();
        Pageable pageable = TicketUtil.createPageable();
        FilterParamsDto filterParamsDto = TicketUtil.createFilterParamsDto();
        Ticket ticket = TicketUtil.createTicketForTicketDto();
        TicketDto ticketDto = TicketUtil.createTicketDto();
        Page<Ticket> expectedPageOfTickets = new PageImpl<>(List.of(ticket));
        Page<TicketDto> expectedPageOfTicketsDto = new PageImpl<>(List.of(ticketDto));

        when(ticketsRepository.findTicketsForEngineer(
            user.getId(), TicketServiceUtil.statesOfEngineerAssignee, filterParamsDto.getId(),
            filterParamsDto.getName(), filterParamsDto.getDesiredDate(), filterParamsDto.getStates(),
            filterParamsDto.getUrgencies(), pageable))
            .thenReturn(expectedPageOfTickets);
        when(ticketMapper.mapTicketInTicketDto(ticket)).thenReturn(ticketDto);

        //when
        Page<TicketDto> actualPageOfTicketsDto = ticketsService.getTicketsForEngineer(user, pageable, filterParamsDto);

        //then
        assertThat(actualPageOfTicketsDto).isEqualTo(expectedPageOfTicketsDto);
    }

    @Test
    void getFilterParamsDto_ShouldPass_WhenActualFilterParamsDtoIsEqualToExpectedFilterParamsDto() {

        //given
        FilterParamsDto expectedFilterParamsDto = TicketUtil.createFilterParamsDto();

        when(filterParamsMapper.mapParamsInFilterParamsDto(
            TicketUtil.ID, TicketUtil.NAME, TicketUtil.DESIRED_DATE_FOR_FILTER_PARAMS,
            TicketUtil.URGENCIES_FOR_FILTER_PARAMS, TicketUtil.STATES_FOR_FILTER_PARAMS))
            .thenReturn(expectedFilterParamsDto);

        //when
        FilterParamsDto actualFilterParamsDto = ticketsService.getFilterParamsDto(
            TicketUtil.ID, TicketUtil.NAME, TicketUtil.DESIRED_DATE_FOR_FILTER_PARAMS,
            TicketUtil.URGENCIES_FOR_FILTER_PARAMS, TicketUtil.STATES_FOR_FILTER_PARAMS);

        //then
        assertThat(actualFilterParamsDto).isEqualTo(expectedFilterParamsDto);
    }
}