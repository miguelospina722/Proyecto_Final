package edu.umanizales.mesadeayuda_u.model;

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
public class Ticket extends EntidadBase {

    private String codigo;
    private String titulo;
    private String descripcion;
    private EstadoTicket estado;
    private Prioridad prioridad;
    private TipoTicket tipo;
    private String categoriaId;
    private String usuarioCreadorId;
    private String asignadoActualId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCierreEstimado;
    private List<Comentario> comentarios;
    private List<Asignacion> historialAsignaciones;
    private List<Adjunto> adjuntos;
    private String slaConfigId;
}
