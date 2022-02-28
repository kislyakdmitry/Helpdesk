package innowise.zuevsky.helpdesk.dto;

import innowise.zuevsky.helpdesk.domain.Attachment;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketUpdateDto {

    @NotBlank
    @Pattern(regexp = "^[a-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|} ]{2,100}$")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|} ]{2,500}$")
    private String description;

    @NotNull
    @FutureOrPresent
    private LocalDate desiredResolutionDate;

    @NotNull
    private int categoryId;

    @NotNull
    private Urgency urgency;

    private List<Attachment> attachments;
}
