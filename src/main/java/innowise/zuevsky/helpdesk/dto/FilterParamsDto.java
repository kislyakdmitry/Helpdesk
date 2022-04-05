package innowise.zuevsky.helpdesk.dto;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterParamsDto {
  private Long id;
  private String name;
  private LocalDate desiredDate;
  private List<Urgency> urgencies;
  private List<State> states;
}
