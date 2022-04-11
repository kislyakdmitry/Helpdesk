package innowise.zuevsky.helpdesk.api;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Feedback")
@RestController
@RequestMapping("/api/feedbacks")
public interface FeedbackApi {

	@ApiOperation(value = "Get Feedback", notes = "Get feedback information according to url id")
	@ApiImplicitParam(name = "feedbackId", value = "Feedback ID", required = true, dataType = "Long", paramType = "path")
	@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
	@GetMapping("/{feedbackId}")
	FeedbackDto getFeedback(@PathVariable Long feedbackId);

	@ApiOperation(value = "Get feedback by ticketId", notes = "Get feedback information according to url ticketId")
	@ApiImplicitParam(name = "ticketId", value = "Ticket ID", required = true, dataType = "Long", paramType = "path")
	@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
	@GetMapping("/feedback/{ticketId}")
	FeedbackDto getFeedbackByTicketId(@PathVariable Long ticketId);

	@ApiOperation(value = "Save feedback", notes = "Save feedback")
	@PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
	@PostMapping
	void saveFeedback(@Valid @RequestBody FeedbackSaveDto createFeedbackDto);

}
