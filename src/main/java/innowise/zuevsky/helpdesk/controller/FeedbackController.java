package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.api.IFeedbackController;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/helpdesk-service/feedbacks")

@RequiredArgsConstructor
public class FeedbackController implements IFeedbackController {

	private final FeedbackService feedbackService;

	@GetMapping("/{feedbackId}")
	@RolesAllowed({ "EMPLOYEE", "MANAGER" })
	@Override
	public FeedbackDto getFeedback(@PathVariable Long feedbackId) {
		return feedbackService.getFeedbackById(feedbackId);
	}

	@GetMapping("/feedback/{ticketId}")
	@RolesAllowed({ "EMPLOYEE", "MANAGER" })
	@Override
	public FeedbackDto getFeedbackByTicketId(@PathVariable Long ticketId) {
		return feedbackService.getFeedbackByTicketId(ticketId);
	}

	@PostMapping
	@RolesAllowed({ "EMPLOYEE", "MANAGER" })
	@Override
	public void saveFeedback(@Valid @RequestBody FeedbackSaveDto createFeedbackDto) {
		feedbackService.saveFeedback(createFeedbackDto);
	}
}
