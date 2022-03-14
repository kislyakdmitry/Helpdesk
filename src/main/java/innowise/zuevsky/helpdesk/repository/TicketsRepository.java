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
    @Query("SELECT t FROM Ticket t WHERE t.ownerId = :id")
    List<Ticket> findTicketsByOwnerId(Long id);

    @Query("SELECT t FROM Ticket t WHERE t.ownerId IN :ownersId")
    List<Ticket> getTicketsByOwnerIdListInStateNew(List<Long> ownersId);

    @Query("SELECT t FROM Ticket t WHERE t.approverId = :approverId AND t.state IN :states")
    List<Ticket> getTicketsByApproverIdInStates(Long approverId, Collection<State> states);
}
