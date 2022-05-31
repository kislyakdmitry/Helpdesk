package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public FeedbackDto mapFeedbackToFeedbackDto(Feedback feedback) {
        return FeedbackDto.builder()
                .date(feedback.getCreated())
                .rate(feedback.getRate())
                .text(feedback.getText())
                .build();
    }

    public Feedback mapFeedbackSaveDtoToFeedback(FeedbackSaveDto saveDto) {
        return Feedback.builder()
                .rate(saveDto.getRate())
                .text(saveDto.getText())
                .ticketId(saveDto.getTicketId())
                .userName(saveDto.getUserName()).build();
    }
}