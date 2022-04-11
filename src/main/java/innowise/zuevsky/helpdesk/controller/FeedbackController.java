package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.api.FeedbackApi;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.service.FeedbackService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackController implements FeedbackApi {

	private final FeedbackService feedbackService;

	@Override
	public FeedbackDto getFeedback(Long feedbackId) {
		return feedbackService.getFeedbackById(feedbackId);
	}

	@Override
	public FeedbackDto getFeedbackByTicketId(Long ticketId) {
		return feedbackService.getFeedbackByTicketId(ticketId);
	}

	@Override
	public void saveFeedback(FeedbackSaveDto createFeedbackDto) {
		feedbackService.saveFeedback(createFeedbackDto);
	}
}
