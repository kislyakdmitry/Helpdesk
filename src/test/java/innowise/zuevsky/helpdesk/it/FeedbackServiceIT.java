package innowise.zuevsky.helpdesk.it;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class FeedbackServiceIT extends BaseIT{

    @Autowired
    FeedbackService feedbackService;

    @Test
    void getFeedbackById_shouldReturnFeedbackDto_whenFeedbackExist(){
        FeedbackDto feedbackDto = feedbackService.getFeedbackById(1L);
        assertThat(feedbackDto.getRate()).isEqualTo(5);
    }

    @Test
    void getFeedbackByTicketId(){
        FeedbackDto feedbackDto = feedbackService.getFeedbackByTicketId(3L);
        assertThat(feedbackDto.getRate()).isEqualTo(5);
        assertThat(feedbackDto.getText()).isEqualTo("Good job!");
    }

}