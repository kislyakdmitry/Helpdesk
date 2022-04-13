package innowise.zuevsky.helpdesk.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackSaveDto {

    @Min(value = 1, message = "Rate should not be less than 1")
    @Max(value = 5, message = "Rate should not be greater than 5")
    private Integer rate;

    @ApiModelProperty(notes = "Feedback text", example = "Thank you for you job!")
    private String text;

    @ApiModelProperty(notes = " Status for is able to provide a feedback for the ticket done.")
    @NotNull
    private Long ticketId;

    @ApiModelProperty(notes = "User ID. User can be as an employee or a manager. "
            + "The user can leave feedback about the ticket with status DONE created by him")
    @NotNull
    private Long userId;
}
