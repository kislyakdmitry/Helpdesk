package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.exception.feedback.*;
import innowise.zuevsky.helpdesk.mapper.FeedbackMapper;
import innowise.zuevsky.helpdesk.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

  private final FeedbackRepository feedbackRepository;
  private final FeedbackMapper feedbackMapper;
  private final TicketsService ticketsService;

  public FeedbackDto getFeedbackById(Long feedbackId) {
    return feedbackRepository
        .findById(feedbackId)
        .map(feedbackMapper::mapFeedbackToFeedbackDto)
        .orElseThrow(() -> new FeedbackNotFoundException(feedbackId));
  }

  public FeedbackDto getFeedbackByTicketId(Long ticketId) {
    return feedbackRepository
        .findFeedbackByTicketId(ticketId)
        .map(feedbackMapper::mapFeedbackToFeedbackDto)
        .orElseThrow(() -> new FeedbackNotFoundException(ticketId));
  }

  public void saveFeedback(FeedbackSaveDto saveFeedbackDto) {
    ticketsService.validateTicketStateDone(saveFeedbackDto.getTicketId());
    ticketsService.validateTicketOwnerBelongUser(
        saveFeedbackDto.getTicketId(), saveFeedbackDto.getUserId());
    checkFeedbackIsNotExists(saveFeedbackDto.getTicketId());
    feedbackRepository.save(feedbackMapper.mapFeedbackSaveDtoToFeedback(saveFeedbackDto));
  }

  private void checkFeedbackIsNotExists(Long ticketId) {
    if (feedbackRepository.existsFeedbackByTicketId(ticketId)) {
      throw new FeedbackExistException(ticketId);
    }
  }
}
