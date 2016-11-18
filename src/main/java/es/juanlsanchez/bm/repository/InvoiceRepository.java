package es.juanlsanchez.bm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import es.juanlsanchez.bm.domain.Invoice;

@Transactional(rollbackFor = Throwable.class)
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  public List<Invoice> findAll();

}
