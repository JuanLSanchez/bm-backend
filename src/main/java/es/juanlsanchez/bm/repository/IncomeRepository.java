package es.juanlsanchez.bm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.juanlsanchez.bm.domain.Income;

public interface IncomeRepository extends JpaRepository<Income, String> {

    @Query("select income from Income income "
	    + "where income.principal.login=?#{principal.username} ")
    public Page<Income> findAllByPrincipal(Pageable pageable);

}
