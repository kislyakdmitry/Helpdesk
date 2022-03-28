package innowise.zuevsky.helpdesk.repository;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    @Query("""
            SELECT t FROM Ticket AS t
            JOIN User AS o ON o.id = t.ownerId
            JOIN User AS a ON a.id = t.approverId
            WHERE t.ownerId = :id
            OR (o.role = 'ROLE_EMPLOYEE' AND t.state = 1)
            OR (t.approverId = :id AND t.state IN :statesOfManagerApprover)
            """)
    Page<Ticket> findTicketsForManager(Long id, Collection<State> statesOfManagerApprover, Pageable pageable);

    @Query("""
            SELECT t FROM Ticket AS t
            JOIN User AS o ON o.id = t.ownerId
            WHERE o.role IN ('ROLE_EMPLOYEE', 'ROLE_MANAGER') AND t.state = 2
            OR (t.assigneeId = :id AND t.state IN :statesOfEngineerAssignee)
            """)
    Page<Ticket> findTicketsForEngineer(Long id, Collection<State> statesOfEngineerAssignee, Pageable pageable);
}
