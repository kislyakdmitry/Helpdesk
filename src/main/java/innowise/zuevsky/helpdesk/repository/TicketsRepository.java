package innowise.zuevsky.helpdesk.repository;

import innowise.zuevsky.helpdesk.domain.Ticket;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TicketsRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

   /* @Query("""
            SELECT t FROM Ticket AS t
            JOIN User AS o ON o.id = t.ownerUserName
            JOIN User AS a ON a.id = t.approverUserName
            WHERE ((t.ownerUserName = :userName) OR (o.role = 'ROLE_EMPLOYEE' AND t.state = 1) OR (t.approverUserName = :userId AND t.state IN :statesOfManagerApprover))
            AND (:ticketId IS NULL OR t.id = :ticketId)
            AND (:ticketName IS NULL OR t.name = :ticketName)
            AND ((CAST(:ticketDesiredDate as date) IS NULL) OR (t.desiredResolutionDate = :ticketDesiredDate))
            AND (t.state IN :ticketStates)
            AND (t.urgency IN :ticketUrgencies)
            """)
    Page<Ticket> findTicketsForManager(String userName, Collection<State> statesOfManagerApprover, Long ticketId,
                                       String ticketName, LocalDate ticketDesiredDate, List<State> ticketStates,
                                       List<Urgency> ticketUrgencies, Pageable pageable);

    @Query("""
            SELECT t FROM Ticket AS t
            JOIN User AS o ON o.id = t.ownerUserName
            WHERE ((o.role IN ('ROLE_EMPLOYEE', 'ROLE_MANAGER') AND t.state = 2) OR (t.assigneeUserName = :userName AND t.state IN :statesOfEngineerAssignee))
            AND (:ticketId IS NULL OR t.id = :ticketId)
            AND (:ticketName IS NULL OR t.name = :ticketName)
            AND ((CAST(:ticketDesiredDate as date) IS NULL) OR (t.desiredResolutionDate = :ticketDesiredDate))
            AND (t.state IN :ticketStates)
            AND (t.urgency IN :ticketUrgencies)
            """)
    Page<Ticket> findTicketsForEngineer(String userName, Collection<State> statesOfEngineerAssignee, Long ticketId,
                                        String ticketName, LocalDate ticketDesiredDate, List<State> ticketStates,
                                        List<Urgency> ticketUrgencies, Pageable pageable);


    */
}
