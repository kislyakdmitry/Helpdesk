package innowise.zuevsky.helpdesk.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {

	@ApiModelProperty(notes = "Feedback creation date")
	private LocalDateTime date;

	@ApiModelProperty(notes = "Rate in feedback from 1 to 5")
	@Min(value = 1, message = "Rate should not be less than 1")
	@Max(value = 5, message = "Rate should not be greater than 5")
	private Integer rate;

	@ApiModelProperty(notes = "Feedback text")
	private String text;
}
