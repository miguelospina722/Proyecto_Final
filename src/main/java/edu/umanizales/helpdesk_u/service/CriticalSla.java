package edu.umanizales.helpdesk_u.service;

import edu.umanizales.helpdesk_u.model.SlaConfig;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CriticalSla implements SlaPolicy {

    @Override
    public LocalDateTime calculateDueDate(SlaConfig slaConfig, LocalDateTime referenceDate) {
        throw new UnsupportedOperationException("Implement critical SLA calculation using CSV configuration");
    }
}
