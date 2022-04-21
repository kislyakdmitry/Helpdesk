package innowise.zuevsky.helpdesk.it.tests.service;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.domain.enums.Role;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.dto.FilterParamsDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.it.BaseIT;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import innowise.zuevsky.helpdesk.service.TicketsService;
import innowise.zuevsky.helpdesk.util.TicketTestUtil;
import innowise.zuevsky.helpdesk.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql({"/data/clear_tickets.sql","/data/insert_tickets.sql"})
class TicketsServiceIT extends BaseIT {

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private TicketsRepository ticketsRepository;

    private final TicketSaveDto ticketSaveDto = TicketTestUtil.createTicketSaveDto();

    private final TicketUpdateDto ticketUpdateDto = TicketTestUtil.createTicketUpdateDto();

    private final User user = UserTestUtil.createTestUser();

    private final Pageable pageable = TicketTestUtil.createPageable();

    private final FilterParamsDto emptyFilterParams = TicketTestUtil.createEmptyFilterParamsDto();

    private final FilterParamsDto filterParams = TicketTestUtil.createFilterParamsDto();

    @Test
    void getTicket_ShouldPass_WhenTicketExists() {

        //when
        TicketDto actualTicket = ticketsService.getTicket(3L);

        //then
        assertNotNull(actualTicket);
        assertThat(actualTicket.getId()).isEqualTo(3L);
        assertThat(actualTicket.getCreatedOn()).isEqualTo(LocalDateTime.parse("2022-03-17T10:00:00.000000"));
    }

    @Test
    void createTicket_ShouldPass_WhenTicketCreatedSuccessfully() {

        //when
        List<Ticket> allTicketsBeforeSaving = ticketsRepository.findAll();
        ticketsService.createTicket(ticketSaveDto);
        List<Ticket> allTicketsAfterSaving = ticketsRepository.findAll();

        //then
        assertThat(allTicketsBeforeSaving.size() + 1).isEqualTo(allTicketsAfterSaving.size());
//        assertNotNull(actualTicketDto);
//        assertThat(actualTicketDto.getDesiredResolutionDate()).isEqualTo(ticketSaveDto.getDesiredResolutionDate());
//        assertThat(actualTicketDto.getDescription()).isEqualTo(ticketSaveDto.getDescription());
    }

    @Test
    void updateTicket_ShouldPass_WhenTicketUpdatedSuccessfully() {

        //when
        ticketsService.createTicket(ticketSaveDto);
        ticketsService.updateTicket(ticketUpdateDto, 1L);
        TicketDto actualTicketDto = ticketsService.getTicket(1L);

        //then
        assertNotNull(actualTicketDto);
        assertThat(actualTicketDto.getName()).isEqualTo(ticketUpdateDto.getName());
        assertThat(actualTicketDto.getDescription()).isEqualTo(ticketUpdateDto.getDescription());
        assertThat(actualTicketDto.getDesiredResolutionDate()).isEqualTo(ticketUpdateDto.getDesiredResolutionDate());
    }

    @Test
    void getAllTickets_ShouldPass_WhenSizeOfTicketPageIsCorrect() {

        //when
        Page<TicketDto> actualPage = ticketsService.getAllTickets(user, pageable, emptyFilterParams);

        //then
        assertNotNull(actualPage);
        assertThat(actualPage.getTotalElements()).isEqualTo(1);
    }

    @Test
    void getMyTickets_ShouldPass_WhenSizeOfTicketPageIsCorrect() {

        //when
        Page<TicketDto> actualPage = ticketsService.getMyTickets(user, pageable, emptyFilterParams);

        //then
        assertNotNull(actualPage);
        assertThat(actualPage.getTotalElements()).isEqualTo(1);
    }

    @Test
    void getTicketsForManager_ShouldPass_WhenSizeOfTicketPageIsCorrect() {

        //when
        Page<TicketDto> actualPage = ticketsService.getTicketsForManager(user, pageable, emptyFilterParams);

        //then
        assertNotNull(actualPage);
        assertThat(actualPage.getTotalElements()).isEqualTo(1);
    }

    @Test
    void getTicketsForEngineer_ShouldPass_WhenSizeOfTicketPageIsCorrect() {

        //when
        Page<TicketDto> actualPage = ticketsService.getTicketsForManager(user, pageable, emptyFilterParams);

        //then
        assertNotNull(actualPage);
        assertThat(actualPage.getTotalElements()).isEqualTo(1);
    }

    @Test
    void getFilterParamsDto_ShouldPass_WhenActualFilterParamsIsCorrect() {

        //when
        FilterParamsDto actualFilterParamsDto = ticketsService.getFilterParamsDto(
                TicketTestUtil.TICKET_ID, TicketTestUtil.NAME, TicketTestUtil.DESIRED_DATE_FOR_FILTER_PARAMS,
                TicketTestUtil.URGENCIES_FOR_FILTER_PARAMS, TicketTestUtil.STATES_FOR_FILTER_PARAMS);

        //then
        assertThat(actualFilterParamsDto.getId()).isEqualTo(filterParams.getId());
        assertThat(actualFilterParamsDto.getName()).isEqualTo(filterParams.getName());
        assertThat(actualFilterParamsDto.getDesiredDate()).isEqualTo(filterParams.getDesiredDate());
    }

    @Test
    void validateTicketStateDone_ShouldPass_WhenTicketStateIsDone() {

        //when
        ticketsService.validateTicketStateDone(5L);
        TicketDto actualTicket = ticketsService.getTicket(5L);

        //then
        assertThat(actualTicket.getState()).isEqualTo(State.DONE);
    }

    @Test
    void validateTicketOwnerBelongUser_ShouldPass_WhenTicketBelongToUser() {

        //when
        ticketsService.validateTicketOwnerBelongUser(7L, user.getId());
        TicketDto actualTicket = ticketsService.getTicket(7L);

        //then
        assertThat(actualTicket.getOwnerId()).isEqualTo(user.getId());
    }
}