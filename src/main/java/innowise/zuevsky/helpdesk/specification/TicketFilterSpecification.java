package innowise.zuevsky.helpdesk.specification;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.jpa.domain.Specification;

public class TicketFilterSpecification {

	private TicketFilterSpecification() {
	}

	public static Specification<Ticket> hasOwnerId(Long ownerId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ownerId"), ownerId);
	}

	public static Specification<Ticket> hasId(Long id) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
	}

	public static Specification<Ticket> hasName(String name) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), name);
	}

	public static Specification<Ticket> hasDesiredDate(LocalDate date) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("desiredResolutionDate"), date);
	}

	public static Specification<Ticket> hasUrgency(Collection<Urgency> urgencies) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("urgency").in(urgencies));
	}

	public static Specification<Ticket> hasState(Collection<State> states) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("state").in(states));
	}
}
