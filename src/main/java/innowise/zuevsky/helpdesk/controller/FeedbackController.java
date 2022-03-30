package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import innowise.zuevsky.helpdesk.service.TicketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final TicketsService ticketsService;

    @GetMapping("/{feedbackId}")
    public FeedbackDto getFeedback(@PathVariable Long feedbackId){
        return feedbackService.getFeedback(feedbackId);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping
    public void saveFeedback(@Valid @RequestBody FeedbackSaveDto saveDto){
        Optional<TicketDto> ticket = Optional.ofNullable(ticketsService.getTicket(saveDto.getTicketId()));
        ticket.ifPresent(ticketDto -> {
            if(!Objects.equals(ticketDto.getState(), State.DONE)) throw new RuntimeException("Status for ticket is not DONE.");
            if(!Objects.equals(ticketDto.getOwnerId(), saveDto.getUserId())) throw new RuntimeException("This user can't create feedback.");
        });

        feedbackService.saveFeedback(saveDto);
    }

}