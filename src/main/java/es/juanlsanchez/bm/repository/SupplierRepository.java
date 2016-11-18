package es.juanlsanchez.bm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import es.juanlsanchez.bm.domain.Supplier;
import es.juanlsanchez.bm.domain.User;

@Transactional(rollbackFor = Throwable.class)
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

  @Query("select supplier from Supplier supplier "
      + "where supplier.principal.login=?#{principal.username} ")
  public Page<Supplier> findAllByPrincipal(Pageable pageable);

  public Optional<Supplier> findOneByIdAndPrincipal(Long id, User principal);

}
