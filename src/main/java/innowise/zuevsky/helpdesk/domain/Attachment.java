package innowise.zuevsky.helpdesk.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "attachments")
public class Attachment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachments_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "link")
	private String link;

	@Column(name = "name")
	private String name;
}
