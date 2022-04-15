package innowise.zuevsky.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import innowise.zuevsky.helpdesk.domain.User;
import innowise.zuevsky.helpdesk.dto.FilterParamsDto;
import innowise.zuevsky.helpdesk.dto.TicketDto;
import innowise.zuevsky.helpdesk.dto.TicketSaveDto;
import innowise.zuevsky.helpdesk.dto.TicketUpdateDto;
import innowise.zuevsky.helpdesk.exception.GlobalExceptionHandler;
import innowise.zuevsky.helpdesk.exception.TicketNotFoundException;
import innowise.zuevsky.helpdesk.mapper.TicketMapper;
import innowise.zuevsky.helpdesk.repository.TicketsRepository;
import innowise.zuevsky.helpdesk.service.TicketsService;
import innowise.zuevsky.helpdesk.service.UsersService;
import innowise.zuevsky.helpdesk.util.TicketUtil;
import innowise.zuevsky.helpdesk.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TicketsControllerTest {

    @InjectMocks
    TicketsController ticketsController;

    @Mock
    TicketsService ticketsService;

    @Mock
    UsersService usersService;

    @Mock
    TicketsRepository ticketsRepository;

    @Mock
    TicketMapper ticketMapper;

    MockMvc mockMvc;

    TicketDto ticketDto = TicketUtil.createTicketDto();
    TicketUpdateDto ticketUpdateDto = TicketUtil.createTicketUpdateDto();
    TicketSaveDto ticketSaveDto = TicketUtil.createTicketSaveDto();
    User user = UserUtil.createTestUser();
    Pageable pageable = TicketUtil.createPageable();
    FilterParamsDto filterParamsDto = TicketUtil.createFilterParamsDto();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketsController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(GlobalExceptionHandler.class)
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

    @Test
    @WithMockUser
    void updateTicket_shouldUpdateTicket() throws Exception {
        //given
        String url = "/api/tickets/{ticketId}";
        //when
        //then
        mockMvc.perform(put(url, TicketUtil.TICKET_ID).contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
                        .content(asJson(ticketUpdateDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void createTicket_shouldCreateTicket() throws Exception {
        String url = "/api/tickets/";

        mockMvc.perform(post(url).contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
                        .content(asJson(ticketSaveDto)))
                .andExpect(status().isOk());

    }

    @Test
    void getAllTickets_shouldReturnAllTickets()throws Exception{
        String url = "/api/tickets/all";
        mockMvc.perform(get(url).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void getMyTickets_shouldReturnMyTickets()throws Exception{
        String url = "/api/tickets/my";

        mockMvc.perform(get(url).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    private String asJson(Object object) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}