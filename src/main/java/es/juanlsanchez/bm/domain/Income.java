package es.juanlsanchez.bm.domain;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(indexes = { @Index(columnList = "incomeDate") })
public class Income extends UserObject {
    // Attributes -------------------------------------------------------------
    @NotNull
    @Past
    private Instant incomeDate;
    @NotBlank
    private String name;
    private String nif;
    private double base;
    @Range(min = 0, max = 100)
    private int iva;

    // Relationships------------------------------------------------------------
}
