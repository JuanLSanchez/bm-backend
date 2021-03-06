package es.juanlsanchez.bm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.juanlsanchez.bm.domain.Income;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.util.object.Pair;
import es.juanlsanchez.bm.web.dto.RangeDTO;

public interface IncomeRepository extends JpaRepository<Income, Long> {

  @Query("select income from Income income "
      + "where income.principal.login=?#{principal.username} ")
  public Page<Income> findAllByPrincipal(Pageable pageable);

  public Optional<Income> findOneByIdAndPrincipal(Long id, User principal);

  @Query("select new es.juanlsanchez.bm.web.dto.RangeDTO(min(income.incomeDate), max(income.incomeDate)) "
      + "from Income income where income.principal.login=?#{principal.username}")
  public RangeDTO getRangeByPrincipal(User principal);

  public List<Income> findAllByPrincipalAndIncomeDateGreaterThanEqualAndIncomeDateLessThanOrderByIncomeDateAsc(
      User principal, LocalDate start, LocalDate finish);

  @Query("select new es.juanlsanchez.bm.util.object.Pair(income.incomeDate, sum(income.base)) "//
      + "from Income income where "//
      + "   income.incomeDate >= :start "//
      + "   and income.incomeDate < :end " + "   and income.principal.login=?#{principal.username} "//
      + "group by income.incomeDate")
  public List<Pair<LocalDate, Double>> evolutionInDaysInTheRange(@Param("start") LocalDate start,
      @Param("end") LocalDate end);

}
