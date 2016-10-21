package es.juanlsanchez.bm.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Section extends UserObject {
	// Attributes -------------------------------------------------------------
	@NotBlank
	private String name;

	//Relationships------------------------------------------------------------
	@Valid
	@OneToMany(mappedBy = "section")
	private Collection<Operation> operations;

}
