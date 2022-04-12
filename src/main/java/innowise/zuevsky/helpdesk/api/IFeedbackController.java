package innowise.zuevsky.helpdesk.api;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Feedback")
@Controller
public interface IFeedbackController {

	@ApiOperation(value = "Get Feedback", notes = "Get feedback information according to url id")
	@ApiImplicitParam(name = "feedbackId", value = "Feedback ID", required = true, dataType = "Long", paramType = "path")
	FeedbackDto getFeedback(@PathVariable Long feedbackId);

	@ApiOperation(value = "Get feedback by ticketId", notes = "Get feedback information according to url ticketId")
	@ApiImplicitParam(name = "ticketId", value = "Ticket ID", required = true, dataType = "Long", paramType = "path")
	FeedbackDto getFeedbackByTicketId(Long ticketId);

	@ApiOperation(value = "Save feedback", notes = "Save feedback")
	void saveFeedback(FeedbackSaveDto createFeedbackDto);

}