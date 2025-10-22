package edu.umanizales.mesadeayuda_u.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class EntidadBase implements Identificable, ExportableCSV {

    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String toCsvRow() {
        return String.join(",",
                id != null ? id : "",
                createdAt != null ? createdAt.toString() : "",
                updatedAt != null ? updatedAt.toString() : "");
    }
}
