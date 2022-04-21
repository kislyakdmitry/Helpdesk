package innowise.zuevsky.helpdesk.util;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import java.time.LocalDateTime;

public class FeedbackUtil {

	public static final int RATE = 5;
	public static final Long FEEDBACK_ID = 1L;
	public static final Long FEEDBACK_ID_NOT_EXIST = 100L;
	public static final Long USER_ID = 2L;

	public static final Long USER_ID_NOT_TICKET_OWNER = 5L;

	public static final Long OWNER_ID = 4L;
	public static final Long TICKET_ID = 3L;
	public static final Long TICKET_ID_STATE_NOT_DONE = 4L;
	public static final Long TICKET_ID_NOT_EXIST = 300L;
	public static final String TEXT = "test text";
	public static final LocalDateTime CREATED = LocalDateTime.of(2022, 4, 1, 12, 0, 0);
	public static final State STATE_DONE = State.DONE;

	public static FeedbackDto createFeedbackDto() {
		return FeedbackDto.builder().date(CREATED).rate(RATE).text(TEXT).build();
	}

	public static Feedback createFeedback() {
		return Feedback.builder().id(FEEDBACK_ID).created(CREATED).rate(RATE).text(TEXT).userId(USER_ID)
				.ticketId(TICKET_ID).build();
	}

	public static FeedbackSaveDto createFeedbackSaveDtoForFeedback() {
		return FeedbackSaveDto.builder().ticketId(TICKET_ID).userId(USER_ID).rate(RATE).text(TEXT).build();
	}

	public static Feedback createFeedbackSaveDtoToFeedback() {
		return Feedback.builder().rate(RATE).text(TEXT).ticketId(TICKET_ID).userId(USER_ID).build();
	}

	public static FeedbackSaveDto createFeedbackSaveDtoIT(){
		return FeedbackSaveDto.builder().ticketId(TICKET_ID).userId(USER_ID).rate(RATE).text("New feedback").build();
	}

	public static FeedbackSaveDto createFeedbackSaveDtoTicketOwnerNotBelongsToUser(){
		return FeedbackSaveDto.builder().ticketId(TICKET_ID).userId(USER_ID_NOT_TICKET_OWNER).rate(RATE).text("New feedback").build();
	}

	public static FeedbackSaveDto createFeedbackSaveDtoTicketStateNotDone(){
		return FeedbackSaveDto.builder().ticketId(TICKET_ID_STATE_NOT_DONE).userId(USER_ID).rate(RATE).text("New feedback").build();
	}

	public static FeedbackSaveDto createFeedbackSaveDtoToSaveFeedback(){
		return FeedbackSaveDto.builder().ticketId(5L).userId(USER_ID).rate(RATE).text("New feedback").build();
	}




}
