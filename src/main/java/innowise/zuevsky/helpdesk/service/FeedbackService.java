package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.exception.FeedbackServiceException;
import innowise.zuevsky.helpdesk.exception.NotFoundException;
import innowise.zuevsky.helpdesk.mapper.FeedbackMapper;
import innowise.zuevsky.helpdesk.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final TicketsService ticketsService;

    public FeedbackDto getFeedback(Long id){
        return feedbackMapper.mapFeedbackToFeedbackDto(feedbackRepository.findById(id).orElseThrow(()->
                new NotFoundException("Feedback Not Found!")));
    }

    public FeedbackDto getFeedbackByTicketId(Long ticketId){
        return  feedbackMapper.mapFeedbackToFeedbackDto( feedbackRepository.findFeedbackByTicketId(ticketId).orElseThrow(()->
                        new NotFoundException("Not found feedback with this ticketId")));
    }

    public void saveFeedback(FeedbackSaveDto saveFeedbackDto){
        Optional<TicketDto> ticket = Optional.ofNullable(ticketsService.getTicket(saveFeedbackDto.getTicketId()));
        ticket.ifPresent(ticketDto -> {
            if(!ticketDto.getState().equals(State.DONE))  throw new FeedbackServiceException("Status for ticket is not DONE.");
            if(!Objects.equals(ticketDto.getOwnerId(),saveFeedbackDto.getUserId())) throw new FeedbackServiceException("This user can't create feedback for this ticket!");
            Optional<Feedback> feedback = feedbackRepository.findFeedbackByTicketId(saveFeedbackDto.getTicketId());
            if(feedback.isPresent()) throw new FeedbackServiceException("Feedback for ticket "+ saveFeedbackDto.getTicketId() + " already exists!");
            feedbackMapper.mapFeedbackToFeedbackDto(feedbackRepository.save(feedbackMapper.mapFeedbackSaveDtoToFeedback(saveFeedbackDto)));
        });
    }

}