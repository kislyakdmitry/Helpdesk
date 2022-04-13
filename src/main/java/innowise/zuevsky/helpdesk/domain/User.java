package innowise.zuevsky.helpdesk.domain;

import innowise.zuevsky.helpdesk.domain.enums.Role;
import innowise.zuevsky.helpdesk.domain.enums.Status;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "first_name", updatable = false)
	private String firstName;

	@Column(name = "last_name", updatable = false)
	private String lastName;

	@Column(name = "email", updatable = false)
	private String email;

	@Column(name = "password", updatable = false)
	private String password;

	@Column(name = "role", updatable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "status", updatable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
}
