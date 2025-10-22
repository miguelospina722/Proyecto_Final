package edu.umanizales.mesadeayuda_u.service;

import edu.umanizales.mesadeayuda_u.model.SLAConfig;

import java.time.LocalDateTime;

public interface SlaPolicy {

    LocalDateTime calcularFechaVencimiento(SLAConfig slaConfig, LocalDateTime fechaReferencia);
}
