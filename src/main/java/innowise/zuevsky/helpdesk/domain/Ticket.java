package innowise.zuevsky.helpdesk.domain;

import innowise.zuevsky.helpdesk.domain.enums.State;
import innowise.zuevsky.helpdesk.domain.enums.Urgency;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|}]{2,100}$")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|}]{2,500}$")
    private String description;

    @NotNull
    @Column(name = "created_on")
    private LocalDate createdOn;

    @NotNull
    @FutureOrPresent
    @Column(name = "desired_resolution_date")
    private LocalDate desiredResolutionDate;

    @NotNull
    @Column(name = "assignee_id")
    private Long assigneeId;

    @NotNull
    @Column(name = "owner_id")
    private Long ownerId;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "state_id")
    private State state;

    @NotNull
    @Column(name = "category_id")
    private int categoryId;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "urgency_id")
    private Urgency urgency;

    @NotNull
    @Column(name = "approver_id")
    private Long approverId;
}
