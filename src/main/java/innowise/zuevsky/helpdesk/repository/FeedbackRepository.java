package innowise.zuevsky.helpdesk.repository;

import innowise.zuevsky.helpdesk.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    @Query("SELECT fb from Feedback fb where fb.ticketId = :ticketId")
    Optional<Feedback> findFeedbackByTicketId(Long ticketId);

}