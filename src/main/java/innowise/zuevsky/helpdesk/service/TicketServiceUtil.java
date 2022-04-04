package innowise.zuevsky.helpdesk.service;

import innowise.zuevsky.helpdesk.domain.enums.State;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketServiceUtil {

    public static final List<State> statesOfManagerApprover = List.of(State.APPROVED, State.DECLINED,
            State.CANCELED, State.IN_PROGRESS, State.DONE);
    public static final List<State> statesOfEngineerAssignee = List.of(State.IN_PROGRESS, State.DONE);
}
