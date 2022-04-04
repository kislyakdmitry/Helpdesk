package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.exception.feedback.*;
import innowise.zuevsky.helpdesk.mapper.FeedbackMapper;
import innowise.zuevsky.helpdesk.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final TicketsService ticketsService;

    public FeedbackDto getFeedback(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .map(feedbackMapper::mapFeedbackToFeedbackDto)
                .orElseThrow(() -> new FeedbackNotFoundException("Feedback with feedbackId="+ feedbackId +" is Not Found!"));
    }

    public FeedbackDto getFeedbackByTicketId(Long ticketId) {
        return feedbackRepository.findFeedbackByTicketId(ticketId)
                .map(feedbackMapper::mapFeedbackToFeedbackDto)
                .orElseThrow(()->new FeedbackNotFoundException("Feedback with ticketId= "+ ticketId +" is Not Found!"));
    }

    public void saveFeedback(FeedbackSaveDto saveFeedbackDto) {
        TicketDto ticket = ticketsService.getTicket(saveFeedbackDto.getTicketId());
        if (checkFeedback(ticket, saveFeedbackDto.getUserId(), saveFeedbackDto.getTicketId())) {
            feedbackRepository.save(feedbackMapper.mapFeedbackSaveDtoToFeedback(saveFeedbackDto));
        }
    }

    private boolean checkFeedback(TicketDto ticket, Long userId, Long ticketId) {
        if (State.DONE.equals(ticket.getState())) {
            return checkTicketBelongsToUser(ticket, userId, ticketId);
        }

        throw new FeedbackTicketStatusException("Status for ticket with ticketId=" + ticketId + " is not DONE!");
    }

    private boolean checkTicketBelongsToUser(TicketDto ticket, Long userId, Long ticketId) {
        if (Objects.equals(ticket.getOwnerId(), userId)) {
            return checkFeedbackExists(ticketId);
        }

        throw new FeedbackTicketBelongsToUserException("User with userId="+ userId +" can't create feedback for ticket with ticketId="+ ticketId +".");
    }

    private boolean checkFeedbackExists(Long ticketId) {
        if (feedbackRepository.existsFeedbackByTicketId(ticketId)) {
            throw new FeedbackExistException("Feedback for ticket with ticketId=" + ticketId + " already exists!");
        }

        return true;
    }
}