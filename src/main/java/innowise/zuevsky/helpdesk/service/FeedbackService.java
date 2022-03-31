package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.exception.FeedbackNotFoundException;
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
        return feedbackMapper.convertFeedbackToFeedbackDto(feedbackRepository.findById(id).orElseThrow(()->
                new FeedbackNotFoundException("Feedback doesn't exist!")));
    }

    public FeedbackDto getFeetbackByTicketId(Long ticketId){
        return  feedbackMapper.convertFeedbackToFeedbackDto( feedbackRepository.findFeedbackByTicketId(ticketId).orElseThrow(()->
                        new FeedbackNotFoundException("Not found feedback with this ticketId")));
    }

    public void createFeedback(FeedbackSaveDto saveDto){
        Optional<TicketDto> ticket = Optional.ofNullable(ticketsService.getTicket(saveDto.getTicketId()));
        ticket.ifPresent(ticketDto -> {
            if(!Objects.equals(ticketDto.getState(), State.DONE)) throw new RuntimeException("Status for ticket is not DONE.");
            if(!Objects.equals(ticketDto.getOwnerId(), saveDto.getUserId())) throw new RuntimeException("This user can't create feedback.");

            feedbackRepository.save(feedbackMapper.convertFeedbackSaveDtoToTicket(saveDto));
        });
    }

}
