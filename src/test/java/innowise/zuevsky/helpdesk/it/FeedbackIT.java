package innowise.zuevsky.helpdesk.it;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.repository.FeedbackRepository;
import innowise.zuevsky.helpdesk.util.FeedbackUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class FeedbackIT extends BaseIT {

    @Autowired
    protected FeedbackRepository feedbackRepository;

    @Test
    void existsFeedbackByTicketId_shouldReturnTrue_whenFeedbackExist() {
        Feedback feedback = feedbackRepository.save(FeedbackUtil.createFeedback());
        boolean existsFeedbackByTicketId = feedbackRepository.existsFeedbackByTicketId(feedback.getTicketId());
        assertThat(existsFeedbackByTicketId).isTrue();
    }

}