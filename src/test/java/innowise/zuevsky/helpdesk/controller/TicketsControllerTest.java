package innowise.zuevsky.helpdesk.controller;

import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.dto.FilterParamsDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.exception.GlobalExceptionHandler;
import innowise.zuevsky.helpdesk.exception.TicketNotFoundException;
import innowise.zuevsky.helpdesk.service.TicketsService;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import innowise.zuevsky.helpdesk.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TicketsControllerTest {

  @InjectMocks
  TicketsController ticketsController;

  @Mock
  TicketsService ticketsService;

  MockMvc mockMvc;

  TicketDto ticketDto = TicketUtil.createTicketDto();
  User user = UserUtil.createTestUser();
  Pageable pageable = TicketUtil.createPageable();
  FilterParamsDto filterParamsDto = TicketUtil.createFilterParamsDto();

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(ticketsController).setControllerAdvice(GlobalExceptionHandler.class)
        .build();
  }

  @Test
  @WithMockUser
  void getTicket_ShouldPass_WhenTicketExist() throws Exception {

    //given
    String url = "/api/tickets/{ticketId}";
    when(ticketsService.getTicket(TicketUtil.TICKET_ID)).thenReturn(ticketDto);

    //then
    mockMvc.perform(get(url, TicketUtil.TICKET_ID))
            .andExpect(jsonPath("$.name").value(ticketDto.getName()))
            .andExpect(jsonPath("$.id").value(ticketDto.getId()));
  }

  @Test
  @WithMockUser
  void getTicket_shouldReturnTicketNotFoundException_whenTicketDoesNotExist() throws Exception {
    // given
    String url = "/api/tickets/{ticketId}";
    when(ticketsService.getTicket(TicketUtil.TICKET_ID))
            .thenThrow(new TicketNotFoundException(TicketUtil.TICKET_ID));
    // then
    mockMvc.perform(get(url, TicketUtil.TICKET_ID)).andExpect(status().isNotFound());
  }
}