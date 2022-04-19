package innowise.zuevsky.helpdesk.repository;

import innowise.zuevsky.helpdesk.HelpdeskApplication;
import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.util.FeedbackUtil;
import innowise.zuevsky.helpdesk.util.HelpdeskPostgresqlContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HelpdeskApplication.class)
class FeedbackRepositoryIntegrationTest {

    @Autowired
    protected FeedbackRepository feedbackRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = HelpdeskPostgresqlContainer.getInstance();

    @Test
    @Transactional
    void existsFeedbackByTicketId_shouldReturnTrue_whenFeedbackExist() {
        //given
        Feedback feedback = feedbackRepository.save(FeedbackUtil.createFeedback());
        //when
        boolean existsFeedbackByTicketId = feedbackRepository.existsFeedbackByTicketId(feedback.getTicketId());
        //then
        assertThat(existsFeedbackByTicketId).isEqualTo(true);
    }

    @Test
    @Transactional
    void existBeedbackByTicketId_shouldReturnFalse_whenFeedbackDoesNotExist(){
        //given
        //when
        boolean actual = feedbackRepository.existsFeedbackByTicketId(FeedbackUtil.TICKET_ID);
        //then
        assertThat(actual).isEqualTo(false);
    }

}