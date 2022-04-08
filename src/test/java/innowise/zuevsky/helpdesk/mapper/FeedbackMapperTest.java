package innowise.zuevsky.helpdesk.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import innowise.zuevsky.helpdesk.domain.Feedback;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.util.FeedbackUtil;
import org.junit.jupiter.api.Test;

class FeedbackMapperTest {

	private final FeedbackMapper feedbackMapper = new FeedbackMapper();

	@Test
	void mapFeedbackToFeedbackDto_shouldReturnFeedbackDto() {
		// given
		Feedback feedback = FeedbackUtil.createFeedback();
		FeedbackDto expectedFeedback = FeedbackUtil.createFeedbackDto();
		// when
		FeedbackDto actualFeedback = feedbackMapper.mapFeedbackToFeedbackDto(feedback);
		// then
		assertThat(expectedFeedback).usingRecursiveComparison().isEqualTo(actualFeedback);
	}

	@Test
	void mapFeedbackSaveDtoToFeedback_shouldReturnFeedback() {
		// given
		FeedbackSaveDto saveDto = FeedbackUtil.createFeedbackSaveDtoForFeedback();
		Feedback expectedFeedback = FeedbackUtil.createFeedbackSaveDtoToFeedback();
		// when
		Feedback actualFeedback = feedbackMapper.mapFeedbackSaveDtoToFeedback(saveDto);
		// then
		assertThat(expectedFeedback).usingRecursiveComparison().isEqualTo(actualFeedback);
	}
}
