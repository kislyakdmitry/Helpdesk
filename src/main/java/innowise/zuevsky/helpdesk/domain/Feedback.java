package innowise.zuevsky.helpdesk.domain;
import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "feedbacks_id_seq")
    @Column(name = "id", nullable = false)

    private Long id;

    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "rate", nullable = false)
    private Integer rate;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "ticket_id")
    private Long ticketId;

}