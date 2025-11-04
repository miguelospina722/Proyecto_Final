package edu.umanizales.helpdesk_u.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class SupportingDocument extends BaseEntity {

    private String fileName;
    private String description;
    private String storagePath;
}
