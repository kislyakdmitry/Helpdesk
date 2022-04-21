package innowise.zuevsky.helpdesk.dto;

import innowise.zuevsky.helpdesk.util.FeedbackTestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FeedbackDtoTest {

    @Test
    void testToString() {
        FeedbackDto feedback = FeedbackTestUtil.createFeedbackDto();
        assertThat(feedback.toString()).hasToString("FeedbackDto(date=2022-04-01T12:00, rate=5, text=test text)");
    }
}