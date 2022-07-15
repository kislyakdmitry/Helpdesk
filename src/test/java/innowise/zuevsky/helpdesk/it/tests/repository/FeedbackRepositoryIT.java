package innowise.zuevsky.helpdesk.it.tests.repository;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.it.BaseIT;
import innowise.zuevsky.helpdesk.repository.FeedbackRepository;
import innowise.zuevsky.helpdesk.util.TicketTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql({"/data/clear_feedbacks.sql", "/data/insert_feedbacks.sql"})
class FeedbackRepositoryIT extends BaseIT {

    @Autowired
    private FeedbackRepository feedbackRepository;

    private final Feedback expectedFeedback = new Feedback(1L, TicketTestUtil.OWNER_NAME, 5, "Good job!", LocalDateTime.parse("2022-04-19T09:51:54.989423"), 3L);

    @Test
    void findFeedbackByTicketId_ShouldPass_WhenCurrentFeedbackIsEqualToExpectedFeedback() {

        //when
        Feedback currentFeedback = feedbackRepository.findFeedbackByTicketId(3L).orElseThrow();

        //then
        assertNotNull(currentFeedback);
        assertThat(currentFeedback.getText()).isEqualTo(expectedFeedback.getText());
        assertThat(currentFeedback.getId()).isEqualTo(expectedFeedback.getId());
    }

    @Test
    void existsFeedbackByTicketId_ShouldPass_WhenFeedbackIsExists() {

        //when
        boolean isFeedbackExist = feedbackRepository.existsFeedbackByTicketId(3L);

        //then
        assertThat(isFeedbackExist).isTrue();
    }

    @Test
    void save_ShouldPass_WhenFeedbackWasSuccessfullySaved() {

        //when
        Feedback currentFeedback = feedbackRepository.save(expectedFeedback);

        //then
        assertNotNull(currentFeedback);
        assertThat(currentFeedback.getText()).isEqualTo(expectedFeedback.getText());
        assertThat(currentFeedback.getCreated()).isEqualTo(expectedFeedback.getCreated());
    }
}