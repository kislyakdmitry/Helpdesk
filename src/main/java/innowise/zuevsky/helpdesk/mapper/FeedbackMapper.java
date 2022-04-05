package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

  public FeedbackDto mapFeedbackToFeedbackDto(Feedback feedback) {
    return FeedbackDto.builder()
        .date(feedback.getDate())
        .rate(feedback.getRate())
        .text(feedback.getText())
        .build();
  }

  public Feedback mapFeedbackSaveDtoToFeedback(FeedbackSaveDto saveDto) {
    return Feedback.builder()
        .date(LocalDateTime.now())
        .ticketId(saveDto.getTicketId())
        .rate(saveDto.getRate())
        .text(saveDto.getText())
        .ticketId(saveDto.getTicketId())
        .userId(saveDto.getUserId())
        .build();
  }
}
