package edu.umanizales.mesadeayuda_u.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Adjunto extends DocumentoSoporte {

    private long tamanioBytes;
    private String tipoContenido;
}
