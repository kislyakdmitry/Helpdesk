package innowise.zuevsky.helpdesk.specification;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public class TicketFilterSpecification {

    public static Specification<Ticket> hasUrgency(Collection<Urgency> urgencies) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("urgency").in(urgencies));
    }

    public static Specification<Ticket> hasOwnerId(Long ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ownerId"), ownerId);
    }
 }
