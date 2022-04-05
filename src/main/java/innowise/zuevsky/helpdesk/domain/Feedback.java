package innowise.zuevsky.helpdesk.domain;

import java.time.LocalDateTime;
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
@Table(name = "feedbacks")
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedbacks_id_seq")
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "user_Id")
  private Long userId;

  @Column(name = "rate", nullable = false)
  private Integer rate;

  @Column(name = "text")
  private String text;

  @Column(name = "created")
  private LocalDateTime date;

  @Column(name = "ticket_id")
  private Long ticketId;
}
