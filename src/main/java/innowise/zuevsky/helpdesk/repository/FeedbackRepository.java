package innowise.zuevsky.helpdesk.repository;

import innowise.zuevsky.helpdesk.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    @Query("SELECT fb FROM Feedback fb WHERE fb.ticketId = :ticketId")
    Optional<Feedback> findFeedbackByTicketId(long ticketId);

    @Query("SELECT CASE WHEN COUNT(fb)> 0 THEN TRUE ELSE FALSE END FROM Feedback fb WHERE fb.ticketId=:ticketId")
    boolean existsFeedbackByTicketId(long ticketId);

}