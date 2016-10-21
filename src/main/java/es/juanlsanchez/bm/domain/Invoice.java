package es.juanlsanchez.bm.domain;

import java.time.Instant;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(indexes = {@Index(columnList = "dateBuy")})
public class Invoice extends UserObject {
	// Attributes -------------------------------------------------------------
	private String number;
	@NotNull
	@Past
	private Instant dateBuy;
	
	//Relationships------------------------------------------------------------
	@Valid
	@ManyToOne(optional = false)
	private Supplier supplier;
	@Valid
	@ManyToOne(optional=false)
	private Operation operation;
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy="invoice")
	private Collection<InvoiceLine> invoiceLines;

	
}
