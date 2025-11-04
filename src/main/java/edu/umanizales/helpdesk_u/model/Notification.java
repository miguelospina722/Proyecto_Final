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
public class Notification extends BaseEntity {

    private User recipient;
    private String message;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;

    public boolean isRead() {
        return readAt != null;
    }
}
