package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.api.IFeedbackController;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/helpdesk-service/feedbacks")
@RequiredArgsConstructor
public class FeedbackController implements IFeedbackController {

	private final FeedbackService feedbackService;

	@PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
	@GetMapping("/{feedbackId}")
	@Override
	public FeedbackDto getFeedback(@PathVariable Long feedbackId) {
		return feedbackService.getFeedbackById(feedbackId);
	}

	@GetMapping("/feedback/{ticketId}")
	@Override
	public FeedbackDto getFeedbackByTicketId(@PathVariable Long ticketId) {
		return feedbackService.getFeedbackByTicketId(ticketId);
	}

	@PostMapping
	@Override
	public void saveFeedback(@Valid @RequestBody FeedbackSaveDto createFeedbackDto) {
		feedbackService.saveFeedback(createFeedbackDto);
	}
}
