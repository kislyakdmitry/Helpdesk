package innowise.zuevsky.helpdesk.domain;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotBlank
    private String name;
    private String description;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "desired_resolution_date")
    private LocalDate desiredResolutionDate;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_id")
    private State state;

    @Column(name = "category_id")
    private int categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "urgency_id")
    private Urgency urgency;

    @Column(name = "approver_id")
    private Long approverId;
}
