package edu.umanizales.helpdesk_u.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Assignment extends BaseEntity {

    private Ticket ticket;
    private User assignedTo;
    private User assignedBy;
    private LocalDateTime assignedAt;
    private String reason;
}
