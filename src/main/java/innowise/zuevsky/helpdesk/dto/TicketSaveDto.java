package innowise.zuevsky.helpdesk.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import innowise.zuevsky.helpdesk.domain.Attachment;
import innowise.zuevsky.helpdesk.domain.enums.Category;
import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketSaveDto {

	@NotBlank
	@Pattern(regexp = "^[a-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|} ]{2,100}$")
	private String name;

	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|} ]{2,500}$")
	private String description;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@NotNull
	@FutureOrPresent
	private LocalDate desiredResolutionDate;

	@NotNull
	private Long assigneeId;

	@NotNull
	private Long ownerId;

	@NotNull
	private State state;

	@NotNull
	private Category category;

	@NotNull
	private Urgency urgency;

	@NotNull
	private Long approverId;

	private List<Attachment> attachments;
}
