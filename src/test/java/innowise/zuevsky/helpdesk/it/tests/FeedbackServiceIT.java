package innowise.zuevsky.helpdesk.it.tests;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.exception.FeedbackExistException;
import innowise.zuevsky.helpdesk.exception.FeedbackNotFoundException;
import innowise.zuevsky.helpdesk.exception.TicketOwnerNotBelongsToUserException;
import innowise.zuevsky.helpdesk.exception.TicketStateNotDoneException;
import innowise.zuevsky.helpdesk.it.BaseIT;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import innowise.zuevsky.helpdesk.util.FeedbackUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Sql({"/data/clear_feedbacks.sql","/data/insert_feedbacks.sql"})
class FeedbackServiceIT extends BaseIT {

    @Autowired
    FeedbackService feedbackService;

    @Test
    void getFeedbackById_shouldReturnFeedbackDto_whenFeedbackExist(){
        FeedbackDto feedbackDto = feedbackService.getFeedbackById(FeedbackUtil.FEEDBACK_ID);
        assertThat(feedbackDto.getRate()).isEqualTo(5);
        assertThat(feedbackDto.getText()).isEqualTo("Good job!");
        assertThat(feedbackDto.getDate()).isEqualTo(LocalDateTime.of(2022, 4, 19, 9, 0, 0));
    }

    @Test
    void getFeedbackById_shouldThrowFeedbackNotFoundException_whenFeedbackDoesNotExist() {
        assertThatThrownBy(() -> feedbackService.getFeedbackById(FeedbackUtil.FEEDBACK_ID_NOT_EXIST))
                .isInstanceOf(FeedbackNotFoundException.class)
                .hasMessage(String.format("Feedback doesn't exist! feedbackId:%s", FeedbackUtil.FEEDBACK_ID_NOT_EXIST));
    }

    @Test
    void getFeedbackByTicketId_shouldReturnFeedbackDto_whenFeedbackExist(){
        FeedbackDto feedbackDto = feedbackService.getFeedbackByTicketId(3L);
        assertThat(feedbackDto.getRate()).isEqualTo(5);
        assertThat(feedbackDto.getText()).isEqualTo("Good job!");
        assertThat(feedbackDto.getDate()).isEqualTo(LocalDateTime.of(2022, 4, 19, 9, 0, 0));
    }

    @Test
    void getFeedbackByTicketId_shouldThrowFeedbackNotFoundException_whenFeedbackDoesNotExist() {
        assertThatThrownBy(()->feedbackService.getFeedbackByTicketId(FeedbackUtil.TICKET_ID_NOT_EXIST))
                .isInstanceOf(FeedbackNotFoundException.class)
                .hasMessage(String.format("Feedback doesn't exist! feedbackId:%s", FeedbackUtil.TICKET_ID_NOT_EXIST));
    }

    @Test
    void saveFeedback_shouldThrowTicketStateNotDoneException(){
        FeedbackSaveDto feedbackSaveDto = FeedbackUtil.createFeedbackSaveDtoTicketStateNotDone();
        assertThatThrownBy(()->feedbackService.saveFeedback(feedbackSaveDto))
                .isInstanceOf(TicketStateNotDoneException.class)
                .hasMessage(String.format("Status for ticket is not DONE! ticketId:%s", feedbackSaveDto.getTicketId()));
    }
    @Test
    void  saveFeedback_shouldThrowTicketOwnerNotBelongsToUserException(){
        FeedbackSaveDto feedbackSaveDto = FeedbackUtil.createFeedbackSaveDtoTicketOwnerNotBelongsToUser();
        assertThatThrownBy(()->feedbackService.saveFeedback(feedbackSaveDto))
                .isInstanceOf(TicketOwnerNotBelongsToUserException.class)
                .hasMessage(String.format("Ticket owner not belongs to user for ticket! ticketId=%s, userId:%s", feedbackSaveDto.getTicketId(),feedbackSaveDto.getUserId()));
    }
    @Test
    void saveFeedback_shouldThrowFeedbackExistException_whenFeedbackExist() {
        FeedbackSaveDto feedbackSaveDto = FeedbackUtil.createFeedbackSaveDtoIT();
        assertThatThrownBy(()->feedbackService.saveFeedback(feedbackSaveDto))
                .isInstanceOf(FeedbackExistException.class)
                .hasMessage(String.format("Feedback already exists! TicketId:%s", feedbackSaveDto.getTicketId()));
    }

}