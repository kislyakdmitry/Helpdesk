package innowise.zuevsky.helpdesk.domain;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String description;
    private LocalDate createdOn;
    private LocalDate desiredResolutionDate;
    private Long assigneeId;
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_id")
    private State state;

    private int categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "urgency_id")
    private Urgency urgency;

    private Long approverId;
}
