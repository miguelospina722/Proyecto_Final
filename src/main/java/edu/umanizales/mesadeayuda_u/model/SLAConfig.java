package edu.umanizales.mesadeayuda_u.model;

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
public class SLAConfig extends EntidadBase {

    private String nombre;
    private Prioridad prioridadObjetivo;
    private Duration tiempoRespuesta;
    private Duration tiempoResolucion;
    private String descripcion;
}
