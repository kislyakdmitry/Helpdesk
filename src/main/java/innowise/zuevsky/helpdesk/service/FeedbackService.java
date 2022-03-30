package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.exception.FeedbackNotFoundException;
import innowise.zuevsky.helpdesk.mapper.FeedbackMapper;
import innowise.zuevsky.helpdesk.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public FeedbackDto getFeedback(Long id){
        return feedbackMapper.convertFeedbackToFeedbackDto(feedbackRepository.findById(id).orElseThrow(()->
                new FeedbackNotFoundException("Feedback doesn't exist!")));
    }

    public FeedbackDto getFeetbackByTicketId(Long ticketId){
        return  feedbackMapper.convertFeedbackToFeedbackDto( feedbackRepository.findFeedbackByTicketId(ticketId).orElseThrow(()->
                        new FeedbackNotFoundException("Not found feedback with this ticketId")));
    }

    public void saveFeedback(FeedbackSaveDto saveDto){
            feedbackRepository.save(feedbackMapper.convertFeedbackSaveDtoToTicket(saveDto));
    }

}
