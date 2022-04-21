package innowise.zuevsky.helpdesk.it.tests.service;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.exception.FeedbackNotFoundException;
import innowise.zuevsky.helpdesk.it.BaseIT;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import innowise.zuevsky.helpdesk.util.FeedbackTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Sql({"/data/clear_feedbacks.sql", "/data/insert_feedbacks.sql"})
class FeedbackServiceIT extends BaseIT {

    @Autowired
    FeedbackService feedbackService;

    @Test
    void getFeedbackById_shouldReturnFeedbackDto_whenFeedbackExist(){
        FeedbackDto feedbackDto = feedbackService.getFeedbackById(FeedbackTestUtil.FEEDBACK_ID);
        assertThat(feedbackDto.getRate()).isEqualTo(5);
    }


    @Test
    void getFeedbackById_shouldThrowFeedbackNotFoundException_whenFeedbackNotExist() {
        assertThatThrownBy(() -> feedbackService.getFeedbackById(FeedbackTestUtil.FEEDBACK_ID_NOT_EXIST))
                .isInstanceOf(FeedbackNotFoundException.class)
                .hasMessage(String.format("Feedback doesn't exist! feedbackId:%s", FeedbackTestUtil.FEEDBACK_ID_NOT_EXIST));
    }

    @Test
    void getFeedbackByTicketId(){
        FeedbackDto feedbackDto = feedbackService.getFeedbackByTicketId(3L);
        assertThat(feedbackDto.getRate()).isEqualTo(5);
        assertThat(feedbackDto.getText()).isEqualTo("Good job!");
    }


    @Test
    void getFeedbackByTicketId_shouldThrowFeedbackNotFoundException_whenFeedbackNotExist() {
        assertThatThrownBy(()->feedbackService.getFeedbackByTicketId(FeedbackTestUtil.TICKET_ID_NOT_EXIST))
                .isInstanceOf(FeedbackNotFoundException.class)
                .hasMessage(String.format("Feedback doesn't exist! feedbackId:%s", FeedbackTestUtil.TICKET_ID_NOT_EXIST));
    }

}