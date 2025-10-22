package edu.umanizales.mesadeayuda_u.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class DocumentoSoporte extends EntidadBase {

    private String nombreArchivo;
    private String descripcion;
    private String rutaAlmacenamiento;
}
