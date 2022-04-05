package innowise.zuevsky.helpdesk.mapper;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import innowise.zuevsky.helpdesk.dto.FilterParamsDto;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class FilterParamsMapper {

  public FilterParamsDto mapParamsInFilterParamsDto(
      Long id, String name, String desiredDate, Urgency[] urgencies, State[] states) {
    return FilterParamsDto.builder()
        .id(id)
        .name(name)
        .desiredDate(desiredDate.isEmpty() ? null : LocalDate.parse(desiredDate))
        .urgencies(Optional.ofNullable(urgencies).map(Arrays::asList).orElse(List.of()))
        .states(Optional.ofNullable(states).map(Arrays::asList).orElse(List.of()))
        .build();
  }
}
