package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.service.FeedbackService;

import javax.validation.Valid;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

	private final FeedbackService feedbackService;

	@ApiOperation(value = "Get Feedback", notes = "Get feedback information according to url id")
	@ApiImplicitParam(name = "feedbackId", value = "Feedback ID", required = true, dataType = "Long", paramType = "path")

	@RequestMapping(value = "/{feedbackId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
	@GetMapping("/{feedbackId}")
	public FeedbackDto getFeedback(@PathVariable Long feedbackId) {
		return feedbackService.getFeedbackById(feedbackId);
	}

	@ApiOperation(value = "Get feedback by ticketId", notes = "Get feedback information according to url ticketId")
	@ApiImplicitParam(name = "ticketId", value = "Ticket ID", required = true, dataType = "Long", paramType = "path")
	@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
	@GetMapping("/feedback/{ticketId}")
	public FeedbackDto getFeedbackByTicketId(@PathVariable Long ticketId) {
		return feedbackService.getFeedbackByTicketId(ticketId);
	}

	@ApiOperation(value = "Save feedback", notes = "Save feedback if valid")
	@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
	@PostMapping
	public void saveFeedback(@Valid @RequestBody FeedbackSaveDto createFeedbackDto) {
		feedbackService.saveFeedback(createFeedbackDto);
	}
}
