package edu.umanizales.mesadeayuda_u.service;

import edu.umanizales.mesadeayuda_u.model.SLAConfig;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SlaCritico implements SlaPolicy {

    @Override
    public LocalDateTime calcularFechaVencimiento(SLAConfig slaConfig, LocalDateTime fechaReferencia) {
        throw new UnsupportedOperationException("Implementar cálculo de SLA crítico usando configuración CSV");
    }
}
