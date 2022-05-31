package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.exception.FeedbackExistException;
import innowise.zuevsky.helpdesk.exception.FeedbackNotFoundException;
import innowise.zuevsky.helpdesk.mapper.FeedbackMapper;
import innowise.zuevsky.helpdesk.repository.FeedbackRepository;
import innowise.zuevsky.helpdesk.util.FeedbackTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private FeedbackMapper feedbackMapper;
    @Mock
    private TicketsService ticketsService;

    private static final Feedback feedback = FeedbackTestUtil.createFeedback();
    private static final FeedbackSaveDto feedbackSaveDto = FeedbackTestUtil.createFeedbackSaveDtoForFeedback();

    @Test
    void getFeedbackById_shouldReturnFeedbackDto_whenFeedbackExist() {
        // given
        FeedbackDto expectedFeedback = FeedbackTestUtil.createFeedbackDto();
        when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));
        when(feedbackMapper.mapFeedbackToFeedbackDto(feedback)).thenReturn(expectedFeedback);
        // when
        FeedbackDto actualFeedback = feedbackService.getFeedbackById(FeedbackTestUtil.FEEDBACK_ID);
        // then
        assertEquals(expectedFeedback, actualFeedback);
        verify(feedbackRepository).findById(feedback.getId());
        verify(feedbackMapper).mapFeedbackToFeedbackDto(feedback);
    }

    @Test
    void getFeedbackById_shouldThrowFeedbackNotFoundException_whenFeedbackNotExist() {
        // when
        // then
        assertThatThrownBy(() -> feedbackService.getFeedbackById(FeedbackTestUtil.FEEDBACK_ID))
                .isInstanceOf(FeedbackNotFoundException.class)
                .hasMessage(String.format("Feedback doesn't exist! feedbackId:%s", FeedbackTestUtil.FEEDBACK_ID));
        verify(feedbackRepository).findById(FeedbackTestUtil.FEEDBACK_ID);
        verify(feedbackMapper, never()).mapFeedbackToFeedbackDto(any(Feedback.class));
    }

    @Test
    void getFeedbackByTicketId_shouldReturnFeedbackDto_whenFeedbackExist() {
        // given
        FeedbackDto expectedFeedback = FeedbackTestUtil.createFeedbackDto();
        when(feedbackRepository.findFeedbackByTicketId(FeedbackTestUtil.TICKET_ID)).thenReturn(Optional.of(feedback));
        when(feedbackMapper.mapFeedbackToFeedbackDto(feedback)).thenReturn(expectedFeedback);
        // when
        FeedbackDto actualFeedback = feedbackService.getFeedbackByTicketId(FeedbackTestUtil.TICKET_ID);
        // then
        assertEquals(expectedFeedback, actualFeedback);
        verify(feedbackRepository).findFeedbackByTicketId(anyLong());
        verify(feedbackMapper).mapFeedbackToFeedbackDto(any(Feedback.class));
    }

    @Test
    void getFeedbackByTicketId_shouldThrowFeedbackNotFoundException_whenFeedbackNotExist() {
        // when
        assertThatThrownBy(()->feedbackService.getFeedbackByTicketId(FeedbackTestUtil.TICKET_ID))
                .isInstanceOf(FeedbackNotFoundException.class)
                .hasMessage(String.format("Feedback doesn't exist! feedbackId:%s", FeedbackTestUtil.TICKET_ID));
        // then
        verify(feedbackRepository).findFeedbackByTicketId(FeedbackTestUtil.TICKET_ID);
        verify(feedbackMapper, never()).mapFeedbackToFeedbackDto(any(Feedback.class));
    }

    @Test
    void saveFeedback_shouldSave_whenFeedbackSaved() {
        // when
        feedbackService.saveFeedback(feedbackSaveDto);
        // then
        verify(ticketsService).validateTicketStateDone(feedbackSaveDto.getTicketId());
        verify(ticketsService).validateTicketOwnerBelongUser(feedbackSaveDto.getTicketId(), feedbackSaveDto.getUserName());
        verify(feedbackRepository).existsFeedbackByTicketId(feedbackSaveDto.getTicketId());
        verify(feedbackMapper).mapFeedbackSaveDtoToFeedback(feedbackSaveDto);
    }

    @Test
    void saveFeedback_shouldThrowFeedbackExistException_whenFeedbackExist() {
        // given
        when(feedbackRepository.existsFeedbackByTicketId(feedbackSaveDto.getTicketId())).thenReturn(true);
        // when
        assertThatThrownBy(()->feedbackService.saveFeedback(feedbackSaveDto))
                .isInstanceOf(FeedbackExistException.class)
                .hasMessage(String.format("Feedback already exists! TicketId:%s", feedbackSaveDto.getTicketId()));
        // then
        verify(ticketsService).validateTicketStateDone(feedbackSaveDto.getTicketId());
        verify(ticketsService).validateTicketOwnerBelongUser(feedbackSaveDto.getTicketId(), feedbackSaveDto.getUserName());
        verify(feedbackRepository).existsFeedbackByTicketId(feedbackSaveDto.getTicketId());
    }
}