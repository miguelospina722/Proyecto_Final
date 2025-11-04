package edu.umanizales.helpdesk_u.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Ticket extends BaseEntity {

    private String code;
    private String title;
    private String description;
    private TicketStatus status;
    private Priority priority;
    private TicketType type;
    private Category category;
    private User createdBy;
    private User currentAssignee;
    private LocalDateTime expectedResolutionAt;
    private List<Comment> comments;
    private List<Assignment> assignmentHistory;
    private List<SupportingDocument> attachments;
    private SlaConfig slaConfig;
}
