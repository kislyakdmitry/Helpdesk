package innowise.zuevsky.helpdesk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    TICKETS_BY_OWNER("ticketsByOwner"),
    TICKETS_BY_ALL_EMPLOYEES_IN_NEW("ticketsByAllEmployeesInNew"),
    TICKETS_BY_APPROVER_IN_STATUSES("ticketsByApproverInStatuses"),
    TICKETS_BY_ALL_EMPLOYEES_AND_MANAGERS_IN_APPROVED("ticketsByAllEmployeesAndManagersInApproved"),
    TICKETS_BY_ASSIGNEE_IN_STATUSES("ticketsByAssigneeInStatuses"),
    TICKET_BY_ID("ticketById"),
    TICKET_CREATE("ticketCreate"),
    TICKET_UPDATE("ticketUpdate");

    private final String permission;
}
