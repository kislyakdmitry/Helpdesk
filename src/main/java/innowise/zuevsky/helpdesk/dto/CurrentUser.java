package innowise.zuevsky.helpdesk.dto;

import innowise.zuevsky.helpdesk.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentUser {

    private String userName;
    private Role role;

}