package innowise.zuevsky.helpdesk.it.tests.repository;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.it.BaseIT;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import innowise.zuevsky.helpdesk.util.RepositoryTestUtil;
import innowise.zuevsky.helpdesk.util.TicketTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql({"/data/clear_tickets.sql", "/data/insert_tickets.sql"})
class TicketsRepositoryIT extends BaseIT {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Test
    void findAll_ShouldPass_WhenMyTicketsAreExist() {

        //when
        Page<Ticket> myTickets = ticketsRepository.findAll(RepositoryTestUtil.SPECIFICATION, RepositoryTestUtil.PAGEABLE);

        //then
        assertNotNull(myTickets);
        assertEquals(7, myTickets.getTotalElements());
    }

    @Test
    void findTicketsForManager_ShouldPass_WhenTicketsForManagerAreExist() {

        //when
        Page<Ticket> ticketsForManager = ticketsRepository.findTicketsForManager(
                RepositoryTestUtil.MANAGER_NAME, RepositoryTestUtil.STATES_OF_MANAGER_APPROVER,
                RepositoryTestUtil.TICKET_ID, RepositoryTestUtil.TICKET_NAME, RepositoryTestUtil.TICKET_DESIRED_DATE,
                RepositoryTestUtil.TICKET_STATES, RepositoryTestUtil.TICKET_URGENCIES, RepositoryTestUtil.PAGEABLE);

        //then
        assertNotNull(ticketsForManager);
        assertEquals(5, ticketsForManager.getTotalElements());
    }

    @Test
    void findTicketsForEngineer_ShouldPass_WhenTicketsForEngineerAreExist() {

        //when
        Page<Ticket> ticketsForEngineer = ticketsRepository.findTicketsForEngineer(
                RepositoryTestUtil.MANAGER_NAME, RepositoryTestUtil.STATES_OF_ENGINEER_ASSIGNEE,
                RepositoryTestUtil.TICKET_ID, RepositoryTestUtil.TICKET_NAME, RepositoryTestUtil.TICKET_DESIRED_DATE,
                RepositoryTestUtil.TICKET_STATES, RepositoryTestUtil.TICKET_URGENCIES, RepositoryTestUtil.PAGEABLE);

        //then
        assertNotNull(ticketsForEngineer);
        assertEquals(2, ticketsForEngineer.getTotalElements());
    }

    @Test
    void save_ShouldPass_WhenTicketWasSuccessfullySaved() {

        //given
        Ticket expectedTicket = TicketTestUtil.createTicketForTicketSaveDto();

        //when
        Ticket currentTicket = ticketsRepository.save(expectedTicket);

        //then
        assertNotNull(currentTicket);
        assertThat(currentTicket.getId()).isEqualTo(expectedTicket.getId());
        assertThat(currentTicket.getCreatedOn()).isEqualTo(expectedTicket.getCreatedOn());
    }
}