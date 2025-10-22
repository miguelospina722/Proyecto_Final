package edu.umanizales.mesadeayuda_u.model;

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
public class Asignacion extends EntidadBase {

    private String ticketId;
    private String asignadoAId;
    private LocalDateTime fechaAsignacion;
    private String motivo;
}
