package edu.umanizales.helpdesk_u.service;

import edu.umanizales.helpdesk_u.model.SlaConfig;

import java.time.LocalDateTime;

public interface SlaPolicy {

    LocalDateTime calculateDueDate(SlaConfig slaConfig, LocalDateTime referenceDate);
}
