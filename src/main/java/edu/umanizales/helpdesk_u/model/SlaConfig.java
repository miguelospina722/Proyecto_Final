package edu.umanizales.helpdesk_u.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SlaConfig extends BaseEntity {

    private String name;
    private Priority targetPriority;
    private Duration responseTime;
    private Duration resolutionTime;
    private String description;
}
