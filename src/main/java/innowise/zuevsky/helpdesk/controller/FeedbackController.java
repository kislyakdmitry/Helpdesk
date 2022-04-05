package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

  private final FeedbackService feedbackService;

  @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
  @GetMapping("/{feedbackId}")
  public FeedbackDto getFeedback(@PathVariable Long feedbackId) {
    return feedbackService.getFeedback(feedbackId);
  }

  @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
  @GetMapping("/feedback/{ticketId}")
  public FeedbackDto getFeedbackByTicketId(@PathVariable Long ticketId) {
    return feedbackService.getFeedbackByTicketId(ticketId);
  }

  @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
  @PostMapping
  public void saveFeedback(@Valid @RequestBody FeedbackSaveDto createFeedbackDto) {
    feedbackService.saveFeedback(createFeedbackDto);
  }
}
