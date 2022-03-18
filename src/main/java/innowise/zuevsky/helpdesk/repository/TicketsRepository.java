package innowise.zuevsky.helpdesk.repository;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket AS t WHERE t.ownerId = :id")
    List<Ticket> findTicketsByOwnerId(Long id);

    @Query("")
    List<Ticket> findTicketsForManager(Long id);

    /*SELECT * FROM tickets AS t
    JOIN users AS o ON o.id = t.owner_id
    JOIN users AS a ON a.id = t.approver_id
    WHERE t.owner_id = :ownerId
    OR (o.role = 'ROLE_EMPLOYEE' AND t.state = 'NEW')
    OR (t.approver_id = :ownerId AND t.state IN ('APPROVED', 'DECLINED', 'CANCELLED', 'IN_PROGRESS', 'DONE'))
    ORDER BY t.state;*/
}
