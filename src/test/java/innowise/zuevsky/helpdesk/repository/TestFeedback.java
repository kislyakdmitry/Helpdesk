package innowise.zuevsky.helpdesk.repository;

import innowise.zuevsky.helpdesk.util.FeedbackUtil;
import innowise.zuevsky.helpdesk.util.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class TestFeedback {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected FeedbackRepository feedbackRepository;

    @Test

    void getFeedback_shouldReturnFeedbackDto_whenFeedbackExist() throws Exception {
        //when
        boolean existsFeedbackByTicketId = feedbackRepository.existsFeedbackByTicketId(FeedbackUtil.TICKET_ID);
        //then
        assertThat(existsFeedbackByTicketId).isEqualTo(true);

    }

}
