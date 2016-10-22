package es.juanlsanchez.bm.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Supplier extends UserObject {
    // Attributes -------------------------------------------------------------
    @NotBlank
    private String name;
    private String nif;

    // Relationships------------------------------------------------------------
    @OneToMany(mappedBy = "supplier")
    private Collection<Invoice> invoices;

}
