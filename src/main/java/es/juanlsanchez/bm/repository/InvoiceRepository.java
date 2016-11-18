package es.juanlsanchez.bm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import es.juanlsanchez.bm.domain.Invoice;
import es.juanlsanchez.bm.domain.User;

@Transactional(rollbackFor = Throwable.class)
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  @Query("select invoice from Invoice invoice "
      + "where invoice.principal.login=?#{principal.username} ")
  public Page<Invoice> findAllByPrincipal(Pageable pageable);

  public Optional<Invoice> findOneByIdAndPrincipal(Long id, User principal);

}
