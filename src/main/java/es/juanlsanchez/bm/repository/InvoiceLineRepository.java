package es.juanlsanchez.bm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.juanlsanchez.bm.domain.InvoiceLine;

public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long> {

}
