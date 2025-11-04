package edu.umanizales.helpdesk_u.model;

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
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private Email email;
    private String phoneNumber;
    private boolean active;
}
