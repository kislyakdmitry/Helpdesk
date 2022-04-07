package innowise.zuevsky.helpdesk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.exception.feedback.FeedbackExistException;
import innowise.zuevsky.helpdesk.exception.feedback.FeedbackNotFoundException;
import innowise.zuevsky.helpdesk.exception.feedback.FeedbackTicketBelongsToUserException;
import innowise.zuevsky.helpdesk.exception.feedback.FeedbackTicketStatusException;
import innowise.zuevsky.helpdesk.mapper.FeedbackMapper;
import innowise.zuevsky.helpdesk.repository.FeedbackRepository;
import innowise.zuevsky.helpdesk.util.FeedbackUtil;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

  @InjectMocks private FeedbackService feedbackService;
  @Mock private FeedbackRepository feedbackRepository;
  @Mock private FeedbackMapper feedbackMapper;
  @Mock private TicketsService ticketsService;

  private static final Feedback feedback = FeedbackUtil.createFeedback();
  private static final FeedbackSaveDto feedbackSaveDto =
      FeedbackUtil.createFeedbackSaveDtoForFeedback();

  public static final TicketDto ticketDtoStateDone =
      TicketUtil.createTicketDto(
          FeedbackUtil.TICKET_ID, FeedbackUtil.STATE_DONE, FeedbackUtil.USER_ID);

  @Test
  void getFeedbackById_shouldReturnFeedbackDto_whenFeedbackExist() {
    // given
    FeedbackDto expectedFeedback = FeedbackUtil.createFeedbackDto();
    when(feedbackRepository.findById(anyLong())).thenReturn(Optional.of(feedback));
    when(feedbackMapper.mapFeedbackToFeedbackDto(feedback)).thenReturn(expectedFeedback);
    // when
    FeedbackDto actualFeedback = feedbackService.getFeedbackById(FeedbackUtil.FEEDBACK_ID);
    // then
    assertEquals(expectedFeedback, actualFeedback);
    verify(feedbackRepository).findById(anyLong());
    verify(feedbackMapper).mapFeedbackToFeedbackDto(any(Feedback.class));
  }

  @Test
  void getFeedbackById_shouldThrowFeedbackNotFoundException_whenFeedbackNotExist() {
    // when
    FeedbackNotFoundException exception =
        assertThrows(
            FeedbackNotFoundException.class,
            () -> {
              feedbackService.getFeedbackById(FeedbackUtil.FEEDBACK_ID);
            });
    // then
    assertInstanceOf(FeedbackNotFoundException.class, exception);
    assertEquals(
        String.format("Feedback doesn't exist! feedbackId:%s", FeedbackUtil.FEEDBACK_ID),
        exception.getMessage());
    verify(feedbackRepository).findById(anyLong());
    verify(feedbackMapper, times(0)).mapFeedbackToFeedbackDto(any(Feedback.class));
  }

  @Test
  void getFeedbackByTicketId_shouldReturnFeedbackDto_whenFeedbackExist() {
    // given
    FeedbackDto expectedFeedback = FeedbackUtil.createFeedbackDto();
    when(feedbackRepository.findFeedbackByTicketId(FeedbackUtil.TICKET_ID))
        .thenReturn(Optional.of(feedback));
    when(feedbackMapper.mapFeedbackToFeedbackDto(feedback)).thenReturn(expectedFeedback);
    // when
    FeedbackDto actualFeedback = feedbackService.getFeedbackByTicketId(FeedbackUtil.TICKET_ID);
    // then
    assertEquals(expectedFeedback, actualFeedback);
    verify(feedbackRepository).findFeedbackByTicketId(anyLong());
    verify(feedbackMapper).mapFeedbackToFeedbackDto(any(Feedback.class));
  }

  @Test
  void getFeedbackByTicketId_shouldThrowFeedbackNotFoundException_whenFeedbackNotExist() {
    // when
    FeedbackNotFoundException exception =
        assertThrows(
            FeedbackNotFoundException.class,
            () -> {
              feedbackService.getFeedbackByTicketId(FeedbackUtil.TICKET_ID);
            });
    // then
    assertInstanceOf(FeedbackNotFoundException.class, exception);
    assertEquals(
        String.format("Feedback doesn't exist! feedbackId:%s", FeedbackUtil.TICKET_ID),
        exception.getMessage());
    verify(feedbackRepository).findFeedbackByTicketId(anyLong());
    verify(feedbackMapper, times(0)).mapFeedbackToFeedbackDto(any(Feedback.class));
  }

  @Test
  void saveFeedback_shouldSave_whenFeedbackSaved() {
    // given
    when(ticketsService.getTicket(FeedbackUtil.TICKET_ID)).thenReturn(ticketDtoStateDone);
    when(feedbackRepository.existsFeedbackByTicketId(FeedbackUtil.TICKET_ID)).thenReturn(false);
    // when
    feedbackService.saveFeedback(feedbackSaveDto);
    // then
    verify(ticketsService).getTicket(anyLong());
    verify(feedbackRepository).existsFeedbackByTicketId(anyLong());
    verify(feedbackMapper).mapFeedbackSaveDtoToFeedback(any(FeedbackSaveDto.class));
  }

  @Test
  void saveFeedback_shouldThrowFeedbackExistException_whenFeedbackExist() {
    // given
    when(ticketsService.getTicket(anyLong())).thenReturn(ticketDtoStateDone);
    when(feedbackRepository.existsFeedbackByTicketId(anyLong())).thenReturn(true);
    // when
    FeedbackExistException exception =
        assertThrows(
            FeedbackExistException.class,
            () -> {
              feedbackService.saveFeedback(feedbackSaveDto);
            });
    // then
    assertInstanceOf(FeedbackExistException.class, exception);
    assertEquals(
        String.format("Feedback already exists! TicketId:%s", feedbackSaveDto.getTicketId()),
        exception.getMessage());

    verify(ticketsService).getTicket(anyLong());
    verify(feedbackRepository).existsFeedbackByTicketId(anyLong());
    verify(feedbackMapper, times(0)).mapFeedbackSaveDtoToFeedback(any(FeedbackSaveDto.class));
  }

  @Test
  void saveFeedback_shouldThrowFeedbackTicketStatusException_whenTicketStatusNotDone() {
    // given
    TicketDto ticketDto =
        TicketUtil.createTicketDto(
            FeedbackUtil.TICKET_ID, FeedbackUtil.STATE_NEW, TicketUtil.OWNER_ID);
    when(ticketsService.getTicket(anyLong())).thenReturn(ticketDto);

    // when
    FeedbackTicketStatusException exception =
        assertThrows(
            FeedbackTicketStatusException.class,
            () -> {
              feedbackService.saveFeedback(feedbackSaveDto);
            });
    // then
    assertInstanceOf(FeedbackTicketStatusException.class, exception);
    assertEquals(
        String.format("Status for ticket is not DONE! ticketId:%s", feedbackSaveDto.getTicketId()),
        exception.getMessage());

    verify(ticketsService).getTicket(anyLong());
    verify(feedbackRepository, times(0)).existsFeedbackByTicketId(anyLong());
    verify(feedbackMapper, times(0)).mapFeedbackSaveDtoToFeedback(any(FeedbackSaveDto.class));
  }

  @Test
  void saveFeedback_shouldThrowFeedbackTicketBelongsToUserException_whenUserNotOwnerTicket() {
    // given
    FeedbackSaveDto feedbackSaveDto = FeedbackUtil.createFeedbackSaveDtoForFeedback();
    TicketDto ticketDtoStateNotDone =
        TicketUtil.createTicketDto(
            FeedbackUtil.TICKET_ID, FeedbackUtil.STATE_DONE, FeedbackUtil.OWNER_ID);
    when(ticketsService.getTicket(anyLong())).thenReturn(ticketDtoStateNotDone);

    // when
    FeedbackTicketBelongsToUserException exception =
        assertThrows(
            FeedbackTicketBelongsToUserException.class,
            () -> {
              feedbackService.saveFeedback(feedbackSaveDto);
            });
    // then
    assertInstanceOf(FeedbackTicketBelongsToUserException.class, exception);
    assertEquals(
        String.format(
            "User can't create feedback for ticket! userId:%s, ticketId=%s",
            feedbackSaveDto.getUserId(), feedbackSaveDto.getTicketId()),
        exception.getMessage());

    verify(ticketsService).getTicket(anyLong());
    verify(feedbackRepository, times(0)).existsFeedbackByTicketId(anyLong());
    verify(feedbackMapper, times(0)).mapFeedbackSaveDtoToFeedback(any(FeedbackSaveDto.class));
  }
}
