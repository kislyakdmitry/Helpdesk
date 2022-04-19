package innowise.zuevsky.helpdesk.it.tests;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.it.HelpdeskPostgresContainer;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("integration")
@SpringBootTest
@Testcontainers
public class FeedbackServiceIT {

    @Autowired
    private FeedbackService feedbackService;

    @Container
    public static HelpdeskPostgresContainer container = HelpdeskPostgresContainer.getInstance()
            .withInitScript("data/helpdeskdb_init.sql");

    @Disabled
    @Test
    public void getFeedbackById_shouldReturnFeedbackDto_whenFeedbackExist(){
        FeedbackDto feedbackDto = feedbackService.getFeedbackById(1L);
        assertThat(feedbackDto.getRate()).isEqualTo(5);
    }

}