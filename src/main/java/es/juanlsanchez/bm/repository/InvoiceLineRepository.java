package es.juanlsanchez.bm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.juanlsanchez.bm.domain.InvoiceLine;
import es.juanlsanchez.bm.domain.User;

public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long> {

  @Query("select invoiceLine from InvoiceLine invoiceLine "
      + "where invoiceLine.invoice.principal.login=?#{principal.username} ")
  public Page<InvoiceLine> findAllByInvoicePrincipal(Pageable pageable);

  public Optional<InvoiceLine> findOneByIdAndInvoicePrincipal(Long id, User principal);

  public Page<InvoiceLine> findAllByInvoiceId(Long invoiceId, Pageable pageable);

}
