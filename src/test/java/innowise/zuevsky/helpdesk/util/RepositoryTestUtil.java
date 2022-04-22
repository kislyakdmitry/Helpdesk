package innowise.zuevsky.helpdesk.util;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static innowise.zuevsky.helpdesk.specification.TicketFilterSpecification.hasOwnerId;
import static org.springframework.data.jpa.domain.Specification.where;

public class RepositoryTestUtil {

    public static final Long EMPLOYEE_ID = 2L;
    public static final Long MANAGER_ID = 4L;
    public static final Specification<Ticket> SPECIFICATION = where(hasOwnerId(EMPLOYEE_ID));
    public static final Collection<State> STATES_OF_MANAGER_APPROVER = List.of(State.APPROVED, State.DECLINED, State.CANCELED,
            State.IN_PROGRESS, State.DONE);
    public static final Collection<State> STATES_OF_ENGINEER_ASSIGNEE = List.of(State.IN_PROGRESS, State.DONE);
    public static final Pageable PAGEABLE = PageRequest.of(0, 5, Sort.by("urgency"));
    public static final Long TICKET_ID = null;
    public static final String TICKET_NAME = null;
    public static final LocalDate TICKET_DESIRED_DATE = null;
    public static final List<State> TICKET_STATES = List.of(State.values());
    public static final List<Urgency> TICKET_URGENCIES = List.of(Urgency.values());
}
