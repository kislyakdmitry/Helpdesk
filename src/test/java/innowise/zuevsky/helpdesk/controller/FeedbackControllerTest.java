package innowise.zuevsky.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import innowise.zuevsky.helpdesk.dto.FeedbackDto;
import innowise.zuevsky.helpdesk.dto.FeedbackSaveDto;
import innowise.zuevsky.helpdesk.service.FeedbackService;
import innowise.zuevsky.helpdesk.util.FeedbackUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {
	@InjectMocks
	FeedbackController feedbackController;

	@Mock
	FeedbackService feedbackService;

	MockMvc mockMvc;

	FeedbackDto feedbackDto = FeedbackUtil.createFeedbackDto();
	FeedbackSaveDto feedbackSaveDto = FeedbackUtil.createFeedbackSaveDtoForFeedback();

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();
	}

	@Test
	@WithMockUser
	void getFeedback_shouldReturnFeedbackDto_whenFeedbackExist() throws Exception {
		// given
		String url = "/api/feedbacks/{feedbackId}";

		when(feedbackService.getFeedbackById(anyLong())).thenReturn(feedbackDto);
		// when
		// then
		mockMvc.perform(get(url, FeedbackUtil.FEEDBACK_ID)).andExpect(jsonPath("$.date").isNotEmpty())
				.andExpect(jsonPath("$.rate").value(feedbackDto.getRate()))
				.andExpect(jsonPath("$.text").value(feedbackDto.getText()));
	}

	@Test
	void getFeedbackByTicketId_shouldReturnFeedback_whenExist() throws Exception {
		// given
		String url = "/api/feedbacks/feedback/{ticketId}";
		when(feedbackService.getFeedbackByTicketId(anyLong())).thenReturn(feedbackDto);
		// when
		// then
		mockMvc.perform(get(url, FeedbackUtil.TICKET_ID)).andExpect(jsonPath("$.date").isNotEmpty())
				.andExpect(jsonPath("$.rate").value(feedbackDto.getRate()))
				.andExpect(jsonPath("$.text").value(feedbackDto.getText()));
	}

	@Test
	void saveFeedback_shouldSaveFeedback() throws Exception {
		// given
		String url = "/api/feedbacks";
		// when
		// then
		mockMvc.perform(
				post(url).contentType(APPLICATION_JSON).accept(APPLICATION_JSON).content(asJson(feedbackSaveDto)))
				.andExpect(status().isOk());
	}

	private String asJson(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
