package es.juanlsanchez.bm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.juanlsanchez.bm.domain.InvoiceLine;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.util.object.Pair;

public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long> {

  @Query("select invoiceLine from InvoiceLine invoiceLine "
      + "where invoiceLine.invoice.principal.login=?#{principal.username} ")
  public Page<InvoiceLine> findAllByInvoicePrincipal(Pageable pageable);

  public Optional<InvoiceLine> findOneByIdAndInvoicePrincipal(Long id, User principal);

  public Page<InvoiceLine> findAllByInvoiceId(Long invoiceId, Pageable pageable);

  @Query("select new es.juanlsanchez.bm.util.object.Pair(invoiceLine.invoice.dateBuy, sum(invoiceLine.base)) "//
      + "from InvoiceLine invoiceLine where "//
      + "   invoiceLine.invoice.dateBuy >= :start "//
      + "   and invoiceLine.invoice.dateBuy < :end "
      + "   and invoiceLine.invoice.principal.login=?#{principal.username} "//
      + "group by invoiceLine.invoice.dateBuy")
  public List<Pair<LocalDate, Double>> evolutionInDaysInTheRange(@Param("start") LocalDate start,
      @Param("end") LocalDate end);

}
