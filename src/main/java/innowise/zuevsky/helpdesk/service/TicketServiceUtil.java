package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.enums.State;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TicketServiceUtil {

    private TicketServiceUtil() {}

    public static final List<State> statesOfManagerApprover = List.of(State.APPROVED, State.DECLINED,
            State.CANCELED, State.IN_PROGRESS, State.DONE);
    public static final List<State> statesOfEngineerAssignee = List.of(State.IN_PROGRESS, State.DONE);

    public static Date convertLocalDateToDateFormat(LocalDate date) {
        Date convertedDate = null;
        if (date != null) {
            convertedDate = Date.valueOf(date);
        }
        return convertedDate;
    }
}
