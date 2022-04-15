package innowise.zuevsky.helpdesk.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationDto {

	@ApiModelProperty(notes = "User email")
	private String email;

	@ApiModelProperty(notes = "User password")
	private String password;
}
