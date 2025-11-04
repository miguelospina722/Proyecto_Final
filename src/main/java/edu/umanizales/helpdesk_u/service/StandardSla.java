package edu.umanizales.helpdesk_u.service;

import edu.umanizales.helpdesk_u.model.SLAConfig;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SlaEstandar implements SlaPolicy {

    @Override
    public LocalDateTime calcularFechaVencimiento(SLAConfig slaConfig, LocalDateTime fechaReferencia) {
        throw new UnsupportedOperationException("Implementar cálculo de SLA estándar usando configuración CSV");
    }
}
