package innowise.zuevsky.helpdesk.util;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.FilterParamsDto;

import java.time.LocalDate;
import java.util.Arrays;

public class FilterParamsUtil {

    public static final Long ID = 666L;
    public static final String NAME = "FilterParams";
    public static final String DESIRED_DATE = "2075-03-18";
    public static final Urgency[] URGENCIES = {Urgency.LOW, Urgency.CRITICAL};
    public static final State[] STATES = {State.NEW, State.DONE};

    public static FilterParamsDto createFilterParamsDto() {
        return FilterParamsDto.builder()
                .id(ID)
                .name(NAME)
                .desiredDate(LocalDate.parse(DESIRED_DATE))
                .urgencies(Arrays.stream(URGENCIES).toList())
                .states(Arrays.stream(STATES).toList())
                .build();
    }
}
