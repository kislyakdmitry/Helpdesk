package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/{feedbackId}")
    public FeedbackDto getFeedback(@PathVariable Long feedbackId){
        return feedbackService.getFeedback(feedbackId);
    }

    @GetMapping("/feedback/{ticketId}")
    public FeedbackDto getFeedbackByTicketId(@PathVariable Long ticketId){
        return feedbackService.getFeedbackByTicketId(ticketId);
    }

    @PostMapping
    public void saveFeedback(@Valid @RequestBody FeedbackSaveDto createFeedbackDto){
        feedbackService.saveFeedback(createFeedbackDto);
    }

}