package innowise.zuevsky.helpdesk.it;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.repository.FeedbackRepository;
import innowise.zuevsky.helpdesk.util.FeedbackUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class FeedbackIT extends DatabaseIT {
    @Autowired
    protected FeedbackRepository feedbackRepository;

    @Test
    @WithMockUser
    public void testMvc() {
    }
}